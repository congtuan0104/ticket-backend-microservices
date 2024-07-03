package com.ticket.notification.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {
    private String Content;
    private String Logo;
    private String userType;
    private LocalDateTime sendingTime;
    private String userId;
}
