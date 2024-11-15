package com.example.demo.auth.provider;

import com.example.demo.config.DomainTestConfig;
import com.example.demo.domain.common.auth.Token;
import com.example.demo.domain.common.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtProviderTest extends DomainTestConfig {
    @Autowired
    private JwtProvider jwtProvider;

    private UserPrincipal expectedPrincipal;

    private Token expectedToken;

    @BeforeEach
    void setUp() {
        expectedPrincipal = new UserPrincipal("dev teller", "devteller123@gmail.com");
        expectedToken = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");
    }

    @Test
    @DisplayName("JWT 생성 시, 인증된 사용자 정보를 받으면 토큰 정보를 반환한다")
    void user_principal_create_token_info_test() {
    }

    @Test
    @DisplayName("JWT 인증 시, 유효한 JWT를 받으면 사용자 정보를 반환한다")
    void valid_token_create_user_principal_test() {
    }

    @Test
    @DisplayName("JWT 검증 시, 만료된 JWT를 받으면 TokenExpiredException을 반환한다")
    void expired_token_throw_token_expired_exception() {
    }

    @Test
    @DisplayName("JWT 검증 시, 잘못된 JWT를 받으면 JWTVerificationException을 반환한다")
    void no_valid_token_throw_jwt_verification_exception() {
    }
}
