package com.ticket.notification.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.firebase.database.*;
import com.ticket.notification.entities.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {
    private final DatabaseReference databaseReference;
    private final ObjectMapper objectMapper;

    public NotificationService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    private Map<String, Object> convertObjectToMap(Object obj) {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    private Map<String, Object> convertDataSnapshotToMap(DataSnapshot dataSnapshot) {
        return objectMapper.convertValue(dataSnapshot.getValue(), new TypeReference<Map<String, Object>>() {});
    }


    public CompletableFuture<Map<String, Object>> createNotification(NotificationRequest request) {
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        String uuid = UUID.randomUUID().toString();
        DatabaseReference newRef = databaseReference.child(uuid);
        Map<String, Object> dataMap = convertObjectToMap(request);

        newRef.setValue(dataMap, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                future.completeExceptionally(databaseError.toException());
            } else {
                newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> result = new HashMap<>(convertDataSnapshotToMap(dataSnapshot));
                        result.put("notificationId", newRef.getKey());
                        future.complete(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        future.completeExceptionally(databaseError.toException());
                    }
                });
            }
        });

        return future;
    }


    public CompletableFuture<Map<String, Object>> readData(String key) {
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> result = new HashMap<>(convertDataSnapshotToMap(dataSnapshot));
                result.put("notificationId", key);
                future.complete(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
    public CompletableFuture<Map<String, Object>> readAllData() {
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Object> dataMap = new HashMap<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        dataMap.put(child.getKey(), convertDataSnapshotToMap(child));
                    }
                    future.complete(dataMap);
                } else {
                    future.complete(new HashMap<>());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }

    public CompletableFuture<Map<String, Object>> updateData(String key, NotificationRequest request) {
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        Map<String, Object> updateData = convertObjectToMap(request);
        DatabaseReference newRef = databaseReference.child(key);


        // Update data
        newRef.updateChildren(updateData, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                future.completeExceptionally(databaseError.toException());
            } else {
                newRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> result = new HashMap<>(convertDataSnapshotToMap(dataSnapshot));
                        result.put("notificationId", key);
                        future.complete(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        future.completeExceptionally(databaseError.toException());
                    }
                });
            }
        });

        return future;
    }

    public CompletableFuture<Void> deleteData(String path) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Delete data
        databaseReference.child(path).removeValue((databaseError, databaseReference) -> {
            if (databaseError == null) {
                future.complete(null);
            } else {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }
}



