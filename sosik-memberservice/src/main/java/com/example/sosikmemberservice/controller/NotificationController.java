package com.example.sosikmemberservice.controller;

import com.example.sosikmemberservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping(value = "/subscribe/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long memberId) {
        return notificationService.subscribe(memberId);
    }

    @GetMapping("/{memberId}")
    public void sendData(@PathVariable Long memberId) {
        notificationService.notify(memberId, "data");
    }

    @DeleteMapping("/{notificationId}")
    public void deleteNotification(@PathVariable Long notificationId) {

        notificationService.deleteNotification(notificationId);
    }


}
