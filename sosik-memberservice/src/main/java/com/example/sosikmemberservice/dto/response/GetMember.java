package com.example.sosikmemberservice.dto.response;

import com.example.sosikmemberservice.model.MemberRole;
import com.example.sosikmemberservice.model.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GetMember {


    private Long memberId;
    private String email;
    private String name;
    private String gender;
    private BigDecimal height;
    private MemberRole role;
    private Integer activityLevel;
    private String nickname;
    private String profileImage;
    private String birthday;
    private Integer tdeeCalculation;

    @Setter
    private List<GetWeight> weightList;

    @Builder
    public GetMember(Long memberId, String email, String name, String gender, BigDecimal height, MemberRole role, Integer activityLevel, String nickname, String profileImage, String birthday, Integer tdeeCalculation, List<GetWeight> weightList) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.role = role;
        this.activityLevel = activityLevel;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.tdeeCalculation = tdeeCalculation;
        this.weightList = weightList;
    }

    public static GetMember create(MemberEntity member) {
        GetMember getMemberDTO = GetMember.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail().getValue())
                .name(member.getName().getValue())
                .gender(member.getGender())
                .height(member.getHeight())
                .role(member.getRole())
                .activityLevel(member.getActivityLevel())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage().getValue())
                .birthday(member.getBirthday())
                .tdeeCalculation(member.getTdeeCalculation())
                .build();
        List<GetWeight> getWeightList = member.getWeight().stream()
                .map(GetWeight::create)
                .collect(Collectors.toList());
        getMemberDTO.setWeightList(getWeightList);
        return getMemberDTO;

    }
}
