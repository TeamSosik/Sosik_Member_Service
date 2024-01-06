package com.example.sosikmemberservice.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;

@Builder
public record RequestMember(
        @NotNull(message = "이메일을 입력해주세요!")
        @jakarta.validation.constraints.Email
        String email,
        @NotNull
        @Size(min = 8,max = 16 ,message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
        String password,
        String name,
        String gender,
        BigDecimal height,
        Integer activityLevel,
        String nickname,
        String birthday,
        Integer tdeeCalculation,
        BigDecimal currentWeight,
        BigDecimal targetWeight

)
{


}
