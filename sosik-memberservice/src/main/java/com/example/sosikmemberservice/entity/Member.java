package com.example.sosikmemberservice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 50, nullable = false)
    private String password;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String gender;
    @Column(precision = 4, scale = 1)
    private BigDecimal height;
    @Column(precision = 1)
    private Integer activityLevel;
    @Column(length = 20, nullable = false)
    private String nickname;
    @Column(length = 255, nullable = true)
    private String profileImage;
    @Column(nullable = false)
    private String birthday;
    @Column(precision = 5)
    private Integer tdeeCalculation;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
