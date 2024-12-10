package com.example.demo.persistence;

import com.example.demo.common.http.RestClientFactory;
import com.example.demo.domain.GoogleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GoogleClientConfig {
    @Value("${oauth.google.baseUrl}")
    private String baseUrl;

    private final RestClientFactory abstractRestClientFactory;

    @Bean
    GoogleClient googleTokenClient() {
        return abstractRestClientFactory.createClient(baseUrl, GoogleClient.class);
    }
}
