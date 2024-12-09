package com.example.demo.web.security.config;

import com.example.demo.common.http.RestClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class GoogleClientConfig {
    private final RestClientFactory abstractRestClientFactory;

    @Bean
    RestClient googleTokenClient() {
        return abstractRestClientFactory.createClient("asdf", RestClient.class);
    }
}
