package com.example.sosikmemberservice.dto.kafka;

import com.example.sosikmemberservice.model.entity.NotificationEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResponseNotification(Long notificationId,
                                   Long redirectingPostId,
                                   LocalDateTime createdAt,
                                   String nickname,
                                   String message
) {
    public static ResponseNotification create(NotificationEntity entity){
        return ResponseNotification.builder()
                .notificationId(entity.getNotificationId())
                .redirectingPostId(entity.getRedirectingPostId())
                .createdAt(entity.getCreatedAt())
                .nickname(entity.getNickname())
                .message(entity.getMessage())
                .build();
    }
}
