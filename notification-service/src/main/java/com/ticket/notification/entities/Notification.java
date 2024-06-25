package com.ticket.notification.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "notification")
public class Notification {
    @JsonSerialize(using =  ToStringSerializer.class)
    @Id
    private ObjectId notificationId;
    private String Content;
    private String Logo;
    private String userType;
    private LocalDateTime sendingTime;
    private String userId;
}
