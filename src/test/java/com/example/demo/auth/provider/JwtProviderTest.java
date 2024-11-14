package com.example.demo.auth.provider;

import com.example.demo.config.DomainTestConfig;
import com.example.demo.domain.common.auth.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtProviderTest extends DomainTestConfig {
    private UserPrincipal userPrincipal;

    private Token token;

    @BeforeEach
    void setUp() {

        token = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");
    }

    @Test
    @DisplayName("인증 완료된 사용자 정보를 받으면 토큰 정보를 반환한다")
    void user_principal_create_token_info_test() {
        // Given
    }

    @Test
    @DisplayName("검증된 Access Token을 받으면 사용자 정보를 반환한다")
    void authenticated_access_token_create_user_principal_test() {
        //
    }

    @Test
    @DisplayName("Refresh Token을 받으면 Access Token을 재발급한다")
    void refresh_token_reissue_test() {
        //
    }

    @Test
    @DisplayName("만료된 JWT를 받으면 TokenExpiredException을 반환한다")
    void expired_token_throw_token_expired_exception() {
        //
    }

    @Test
    @DisplayName("잘못된 JWT를 받으면 JWTVerificationException을 반환한다")
    void no_valid_token_throw_jwt_verification_exception() {
        //
    }
}
