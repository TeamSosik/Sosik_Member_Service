package com.example.sosikmemberservice.model.entity;

import com.example.sosikmemberservice.dto.request.RequestUpdateMember;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "weight")
public class WeightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(precision = 4, scale = 1)
    private BigDecimal currentWeight;

    @Column(precision = 4, scale = 1)
    private BigDecimal targetWeight;

    @Builder
    public WeightEntity(
            final Long id,
            final MemberEntity member,
            final BigDecimal currentWeight,
            final BigDecimal targetWeight
    ){
        this.id = id;
        this.member= member;
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
    }

    public static WeightEntity create(BigDecimal currentWeight, BigDecimal targetWeight) {

        return WeightEntity.builder()
                .currentWeight(currentWeight)
                .targetWeight(targetWeight)
                .build();
    }


    public void updateWeight(RequestUpdateMember updateMember){
        this.targetWeight=updateMember.targetWeight();
        this.currentWeight=updateMember.currentWeight();
    }

    // 연관관계 메서드
    public void addMember(MemberEntity member) {

        this.member = member;
        member.getWeight().add(this);

    }
}