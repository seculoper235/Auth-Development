package com.example.demo.web.security.config;

import com.example.demo.common.http.RestClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class KakaoClientConfig {
    private final RestClientFactory restClientFactory;

    @Bean
    RestClient kakaoTokenClient() {
        return restClientFactory.createClient("asdf", RestClient.class);
    }
}
