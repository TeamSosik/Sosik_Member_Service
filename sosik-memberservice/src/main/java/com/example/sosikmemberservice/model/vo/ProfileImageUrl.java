package com.example.sosikmemberservice.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor()
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ProfileImageUrl {
    @Column(name = "profileImage",nullable = false)
    private String value;
}
