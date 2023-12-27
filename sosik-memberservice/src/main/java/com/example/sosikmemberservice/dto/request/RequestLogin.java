package com.example.sosikmemberservice.dto.request;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestLogin(@NotNull(message = "이메일을 입력해주세요!")
                           String email,
                           @NotNull
                           @Size(min =8,max = 16 ,message = "비밀번호는 8자 이상, 16자 이하 입니다.")
                           String password) {
}
