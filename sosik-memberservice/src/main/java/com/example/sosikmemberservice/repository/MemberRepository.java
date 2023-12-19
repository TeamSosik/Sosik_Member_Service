package com.example.sosikmemberservice.repository;

import com.example.sosikmemberservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
