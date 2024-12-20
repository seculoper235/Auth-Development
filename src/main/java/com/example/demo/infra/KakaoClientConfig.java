package com.example.demo.infra;

import com.example.demo.common.http.RestClientFactory;
import com.example.demo.domain.kakao.KakaoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KakaoClientConfig {
    @Value("${oauth.kakao.baseUrl}")
    private String baseUrl;

    private final RestClientFactory restClientFactory;

    @Bean
    KakaoClient kakaoClient() {
        return restClientFactory.createClient(baseUrl, KakaoClient.class);
    }
}
