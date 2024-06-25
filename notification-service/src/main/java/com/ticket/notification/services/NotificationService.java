package com.ticket.notification.services;

import com.ticket.notification.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService (final NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

}
