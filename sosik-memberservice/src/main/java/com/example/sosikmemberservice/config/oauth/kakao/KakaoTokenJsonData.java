package com.example.sosikmemberservice.config.oauth.kakao;

import com.example.sosikmemberservice.dto.response.oauth.kakao.ResponseKakaoToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component

public class KakaoTokenJsonData {
    private WebClient webClient;
    private String REDIRECT_URI;
    private String CLIENT_ID;
    private String GRANT_TYPE = "authorization_code";
    private String TOKEN_URI = "https://kauth.kakao.com/oauth/token";


    public KakaoTokenJsonData(final WebClient webClient,
                         @Value("${REST_API_KEY}") final String clientId,
                         @Value("${REDIRECT_URI}") final String redirectUri){
        this.webClient = webClient;
        this.CLIENT_ID = clientId;
        this.REDIRECT_URI = redirectUri;
    }

    public ResponseKakaoToken getToken(String code) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
        System.out.println(uri);

        Flux<ResponseKakaoToken> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ResponseKakaoToken.class);

        return response.blockFirst();
    }
}
