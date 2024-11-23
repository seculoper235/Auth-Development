package com.example.demo.auth.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.config.ServiceTestEnv;
import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.token.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class JwtFilterTest extends ServiceTestEnv {
    @InjectMocks
    private JwtFilter jwtFilter;

    @Spy
    private MockHttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("Secured API 요청 시, 헤더에 토큰이 주어지지 않았다면 Authentication Token을 발급하지 않는다")
    void not_provide_token_then_not_issue_token() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Secured API 요청 시, 헤더에 주어진 토큰으로 인증에 성공했다면 인증된 Authentication Token을 발급한다")
    void provide_authenticated_token_then_issue_token() throws ServletException, IOException {
        String validToken = "VALID_TOKEN";
        UserPrincipal principal = new UserPrincipal(1L, "devteller123@gmail.com");

        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + validToken);
        given(jwtProvider.verifyToken(any())).willReturn(principal);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).isTrue();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("Secured API 요청 시, 주어진 토큰으로 인증에 실패했다면 에러를 던진다")
    void provide_invalid_token_throws_exception() {
        String invalidToken = "INVALID_TOKEN";

        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + invalidToken);
        given(jwtProvider.verifyToken(invalidToken))
                .willThrow(new JWTVerificationException("올바르지 않은 토큰입니다"));

        assertThrows(JWTVerificationException.class, () ->
                jwtFilter.doFilterInternal(request, response, filterChain));
    }
}
