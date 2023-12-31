package com.example.sosikmemberservice.model.entity;

import com.example.sosikmemberservice.dto.request.UpdateMember;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.model.vo.Name;
import com.example.sosikmemberservice.model.vo.ProfileImageUrl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "weight")
public class WeightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(precision = 4, scale = 1)
    private BigDecimal currentWeight;

    @Column(precision = 4, scale = 1)
    private BigDecimal goalWeight;

    @Builder
    public WeightEntity(
            final Long id,
            final MemberEntity member,
            final BigDecimal currentWeight,
            final BigDecimal goalWeight
    ){
            this.id = id;
            this.member= member;
            this.currentWeight = currentWeight;
            this.goalWeight = goalWeight;
    }


    public void updateWeight(UpdateMember updateMember){
        this.goalWeight=updateMember.goalWeight();
        this.currentWeight=updateMember.currentWeight();
    }
}
