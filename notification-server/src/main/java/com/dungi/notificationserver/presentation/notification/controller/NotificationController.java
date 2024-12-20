package com.dungi.notificationserver.presentation.notification.controller;

import com.dungi.notificationserver.application.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{memberId}", produces = "text/event-stream")
    SseEmitter subscribe(@PathVariable String memberId) {
        return notificationService.subscribe(memberId);
    }
}
