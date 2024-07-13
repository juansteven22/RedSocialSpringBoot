package com.example.socialnetwork.controller;

import com.example.socialnetwork.model.Notification;
import com.example.socialnetwork.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getNotificationsForUser(@PathVariable String userId) {
        return notificationService.getNotificationsForUser(userId);
    }

    @PostMapping("/{notificationId}/read")
    public void markNotificationAsRead(@PathVariable String notificationId) {
        notificationService.markNotificationAsRead(notificationId);
    }
}