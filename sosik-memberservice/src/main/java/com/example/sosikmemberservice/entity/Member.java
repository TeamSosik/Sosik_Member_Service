package com.example.sosikmemberservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;
    @Column(length = 50,nullable = false)
    private String email;
    @Column(length = 50,nullable = false)
    private String password;
    private String name;
    private String gender;
    @Column(precision =4, scale = 1)
    private BigDecimal height;

    private Integer activityLevel;
    private String nickname;
    private String profileImage;
    private LocalDateTime birthday;
    private Integer tdeeCalculation;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
