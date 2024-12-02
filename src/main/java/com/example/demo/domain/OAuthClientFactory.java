package com.example.demo.domain;

import com.example.demo.common.util.RestClientFactory;
import com.example.demo.persistence.OAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO: OAuthClient Bean 생성이 강제되지 않음. 좀 더 나은 구조 고려 필요
@Configuration
public class OAuthClientFactory extends RestClientFactory {

    public OAuthClientFactory(
            @Value("${api.oauth.url}") String url) {
        super(url);
    }

    /* Domain */
    // API 구현
    // JpaRepository 인터페이스 구현체와 같은 역할을 한다
    @Bean
    public OAuthClient oAuthClient() {
        return httpServiceProxyFactory().createClient(OAuthClient.class);
    }
}
