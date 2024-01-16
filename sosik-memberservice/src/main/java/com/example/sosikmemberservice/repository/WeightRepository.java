package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.entity.WeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeightRepository extends JpaRepository<WeightEntity, Long> {
    Optional<WeightEntity> findTopByOrderByCreatedAtDesc();
    Optional<WeightEntity> findByMemberAndCreatedAtBetween(MemberEntity member, LocalDateTime start, LocalDateTime end);
}