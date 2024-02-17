package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.model.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {
}
