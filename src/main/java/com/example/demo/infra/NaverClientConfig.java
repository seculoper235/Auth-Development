package com.example.demo.infra;

import com.example.demo.common.http.RestClientFactory;
import com.example.demo.domain.naver.NaverClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NaverClientConfig {
    @Value("${oauth.naver.baseUrl}")
    private String baseUrl;

    private final RestClientFactory restClientFactory;

    @Bean
    NaverClient naverClient() {
        return restClientFactory.createClient(baseUrl, NaverClient.class);
    }
}
