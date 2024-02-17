package com.example.sosikmemberservice.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class NotificationEntity extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    @Column(nullable = false)
    private Long receiverId;
    @Column(nullable = false)
    private Long redirectingPostId;
    @Column(nullable = true)
    private Boolean isRead;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = true, length = 1000)
    private String message;


    @Builder
    public NotificationEntity(
            final Long receiverId,
            final Long redirectingPostId,
            final Boolean isRead,
            final String nickname,
            final String message
    ) {
        this.receiverId = receiverId;
        this.redirectingPostId = redirectingPostId;
        this.isRead = isRead;
        this.nickname = nickname;
        this.message = message;
    }
}
