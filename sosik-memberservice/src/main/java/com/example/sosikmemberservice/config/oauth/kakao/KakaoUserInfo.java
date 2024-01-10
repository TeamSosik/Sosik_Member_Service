package com.example.sosikmemberservice.config.oauth.kakao;

import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class KakaoUserInfo {
    private final WebClient webClient;
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public ResponseKakaoUserInfo getUserInfo(String token) {
        String uri = USER_INFO_URI;

        Flux<ResponseKakaoUserInfo> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(ResponseKakaoUserInfo.class);

        return response.blockFirst();
    }
}
