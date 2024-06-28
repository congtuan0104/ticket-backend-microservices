package com.ticket.notification.controllers;

import com.google.firebase.database.DataSnapshot;
import com.ticket.notification.entities.NotificationRequest;
import com.ticket.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createData(@RequestBody NotificationRequest request) {
        try {
            request.setSendingTime(LocalDateTime.now());
            Map<String, Object> createdData = notificationService.createNotification(request).get();
            if(createdData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> getNotificationById(@PathVariable("id") String id) {
        try {
            Map<String, Object> dataSnapshot = notificationService.readData(id).get();
            if(dataSnapshot.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(dataSnapshot,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    ResponseEntity<Map<String, Object>> getAllNotification() {
        try {
            Map<String, Object> dataSnapshot = notificationService.readAllData().get();
            return new ResponseEntity<>(dataSnapshot,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Map<String, Object>> updateNotification(
            @PathVariable("id") String id,
            @RequestBody NotificationRequest request) {
        try {
            Map<String, Object> dataSnapshot = notificationService.updateData(id,request).get();
            if(dataSnapshot.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(dataSnapshot,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteNotification(
            @PathVariable("id") String id
    ) {
        try {
             this.notificationService.deleteData(id).get();
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
