package com.example.demo.auth.service;

import com.example.demo.config.ServiceTestEnv;
import com.example.demo.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class SecurityServiceTest extends ServiceTestEnv {
    @InjectMocks
    private SecurityService securityService;

    @Test
    @DisplayName("이메일로 로그인 시, ID / PW가 일치한다면 토큰을 발급한다")

    @Test
    @DisplayName("이메일로 로그인 시, ID / PW가 일치하지 않는다면 에러를 던진다")

    @Test
    @DisplayName("로그아웃 시, 인증 토큰을 삭제한다")
}
