package com.example.demo.web.security.config;

import com.example.demo.common.http.RestClientFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class NaverClientConfig {
    private final RestClientFactory restClientFactory;

    @Bean
    RestClient naverTokenClient() {
        return restClientFactory.createClient("asdf", RestClient.class);
    }
}
