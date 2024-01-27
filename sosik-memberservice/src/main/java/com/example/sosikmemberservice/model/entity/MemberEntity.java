package com.example.sosikmemberservice.model.entity;


import com.example.sosikmemberservice.dto.request.RequestSignup;
import com.example.sosikmemberservice.dto.request.RequestUpdateMember;
import com.example.sosikmemberservice.dto.request.RequestUpdateOAuthMember;
import com.example.sosikmemberservice.model.MemberRole;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.model.vo.Name;
import com.example.sosikmemberservice.model.vo.ProfileImageUrl;
import com.example.sosikmemberservice.util.file.ResultFileStore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Embedded
    private Email email;
    @Setter
    @Column(length = 255, nullable = false)
    private String password;
    @Embedded
    private Name name;
    @Column(nullable = true)
    private String gender;
    @Column(precision = 4, scale = 1)
    private BigDecimal height;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    @Column(precision = 1)
    private Integer activityLevel;
    @Column(length = 20, nullable = false)
    private String nickname;

    @Embedded
    private ProfileImageUrl profileImage;

    @Column(nullable = false)
    private String birthday;

    @Column(precision = 5)
    private Integer tdeeCalculation;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<WeightEntity> weight = new ArrayList<>();

    public MemberEntity(
            final Long memberId,
            final Email email,
            final Name name,
            final String password,
            final String gender,
            final BigDecimal height,
            final Integer activityLevel,
            final String nickname,
            final ProfileImageUrl profileImage,
            final String birthday,
            final Integer tdeeCalculation
    ) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.height = height;
        this.activityLevel = activityLevel;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.tdeeCalculation = tdeeCalculation;

    }

    public static WeightEntity getLastWeightEntity(MemberEntity member){
       return member.getWeight().get(member.getWeight().size() - 1);
    }

    public static MemberEntity buildMember(RequestSignup memberDTO,BCryptPasswordEncoder encoder, ResultFileStore resultFileStore){
        MemberEntity member = MemberEntity.builder()
                .name(memberDTO.name())
                .password(encoder.encode(memberDTO.password()))
                .gender(memberDTO.gender())
                .email(memberDTO.email())
                .height(memberDTO.height())
                .activityLevel(memberDTO.activityLevel())
                .nickname(memberDTO.nickname())
                .profileImage(resultFileStore.folderPath() + "/" + resultFileStore.storeFileName())
                .birthday(memberDTO.birthday())
                .tdeeCalculation(memberDTO.tdeeCalculation())
                .build();

        return member;
    }

    public void updateMember(RequestUpdateMember updateMember) {
        this.height = updateMember.height();
        this.activityLevel = updateMember.activityLevel();
        this.tdeeCalculation = updateMember.tdeeCalculation();
    }

    public void updateOAuthMember(RequestUpdateOAuthMember updateMember) {
        this.height = updateMember.height();
        this.activityLevel = updateMember.activityLevel();
        this.gender = updateMember.gender();
        this.birthday = updateMember.birthday();
        this.tdeeCalculation = updateMember.tdeeCalculation();
    }

    public void updateProfileUrl(ResultFileStore resultFileStore) {
        this.profileImage = new ProfileImageUrl(resultFileStore.folderPath() + "/" + resultFileStore.storeFileName());
    }

    @Builder
    public MemberEntity(
            final Long memberId,
            final String email,
            final String name,
            final String password,
            final String gender,
            final BigDecimal height,
            final Integer activityLevel,
            final String nickname,
            final String profileImage,
            final String birthday,
            final Integer tdeeCalculation
    ) {
        this(memberId,
                new Email(email),
                new Name(name),
                password,
                gender,
                height,
                activityLevel,
                nickname,
                new ProfileImageUrl(profileImage),
                birthday,
                tdeeCalculation);
    }

}
