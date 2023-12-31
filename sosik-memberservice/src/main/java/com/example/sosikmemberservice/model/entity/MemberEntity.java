package com.example.sosikmemberservice.model.entity;


import com.example.sosikmemberservice.dto.request.UpdateMember;
import com.example.sosikmemberservice.model.vo.Email;
import com.example.sosikmemberservice.model.vo.Name;
import com.example.sosikmemberservice.model.vo.ProfileImageUrl;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Embedded
    @Setter
    private Email email;
    @Column(length = 255, nullable = false)
    private String password;
    @Embedded
    private Name name;
    @Column(nullable = true)
    private String gender;
    @Column(precision = 4, scale = 1)
    @Setter
    private BigDecimal height;

    @Column(precision = 1)
    @Setter
    private Integer activityLevel;
    @Column(length = 20, nullable = false)
    @Setter
    private String nickname;

    @Embedded
    private ProfileImageUrl profileImage;

    @Column(nullable = false)
    private String birthday;

    @Column(precision = 5)
    private Integer tdeeCalculation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weight")
    private List<WeightEntity> weights;

    public MemberEntity(final Email email,
                        final Name name,
                        final String password,
                        final String gender,
                        final BigDecimal height,
                        final Integer activityLevel,
                        final String nickname,
                        final ProfileImageUrl profileImage,
                        final String birthday,
                        final Integer tdeeCalculation
    ){
        this.email = email;
        this.name = name;
        this.password=password;
        this.gender = gender;
        this.height = height;
        this.activityLevel = activityLevel;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.tdeeCalculation =tdeeCalculation;

    }
    public void updateMember(UpdateMember updateMember){
        this.height=updateMember.height();
        this.activityLevel=updateMember.activityLevel();
        this.nickname=updateMember.nickname();
        this.profileImage= new ProfileImageUrl(updateMember.profileImage());
    }
    @Builder
    public MemberEntity(
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
    ){
        this(new Email(email),new Name(name),password,gender,height,activityLevel,nickname,new ProfileImageUrl(profileImage),birthday,tdeeCalculation);
    }

}
