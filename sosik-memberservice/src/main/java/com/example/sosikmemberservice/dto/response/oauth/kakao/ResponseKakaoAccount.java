package com.example.sosikmemberservice.dto.response.oauth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseKakaoAccount( @JsonProperty("profile_nickname_needs_agreement")
                                    boolean profileNicknameNeedsAgreement,
                                    ResponseKakaoProfile profile,
                                    @JsonProperty("has_email")
                                    boolean hasEmail,
                                    @JsonProperty("email_needs_agreement")
                                    boolean emailNeedsAgreement,
                                    @JsonProperty("is_email_valid")
                                    boolean isEmailValid,
                                    @JsonProperty("is_email_verified")
                                    boolean isEmailVerified,
                                    String email) {
}
