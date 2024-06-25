package com.ticket.notification.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {
    private String Content;
    private String Logo;
    private String userId;
    private String userType;
}
