package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.model.entity.MemberEntity;
import com.example.sosikmemberservice.model.vo.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(Email email);
    Boolean existsByEmail(Email email);
}
