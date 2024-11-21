package com.example.demo.auth.service;

import com.example.demo.config.ServiceTestEnv;
import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.security.RefreshToken;
import com.example.demo.model.common.security.TokenInfo;
import com.example.demo.model.common.security.UserPrincipal;
import com.example.demo.persistence.RedisRepository;
import com.example.demo.service.TokenService;
import com.example.demo.web.exception.domain.TokenNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class TokenServiceTest extends ServiceTestEnv {
    @InjectMocks
    private TokenService tokenService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RedisRepository redisRepository;

    private UserPrincipal expectedPrincipal;

    private TokenInfo expectedTokenInfo;

    @BeforeEach
    void setUp() {
        expectedPrincipal = new UserPrincipal(1L, "devteller123@gmail.com");
        expectedTokenInfo = new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN");
    }

    @Test
    @DisplayName("토큰 생성 시, 리프레시 토큰으로 인증정보를 얻어냈다면 토큰 정보를 반환한다")
    void user_principal_create_token_info() {
        UserPrincipal principal = new UserPrincipal(1L, "devteller123@gmail.com");
        TokenInfo tokenInfo = new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN");
        RefreshToken refreshToken = new RefreshToken("REFRESH_TOKEN", principal);

        given(jwtProvider.createToken(any(), any())).willReturn(tokenInfo.accessToken());
        given(jwtProvider.createToken(any())).willReturn(tokenInfo.refreshToken());
        given(redisRepository.save(any())).willReturn(refreshToken);

        TokenInfo result = tokenService.createToken(principal);

        assertThat(result).isEqualTo(expectedTokenInfo);
    }

    @Test
    @DisplayName("토큰 재발급 시, 리프레시 토큰으로 인증정보를 얻어냈다면 어세스 토큰을 재발급한다")
    void refresh_token_reissue_access_token() throws TokenNotFoundException {
        UserPrincipal principal = new UserPrincipal(1L, "devteller123@gmail.com");
        RefreshToken refreshToken = new RefreshToken("REFRESH_TOKEN", principal);
        TokenInfo tokenInfo = new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN");

        given(redisRepository.findById(any())).willReturn(Optional.of(refreshToken));
        given(jwtProvider.createToken(any(), any())).willReturn(tokenInfo.accessToken());

        String result = tokenService.reissueToken(tokenInfo.refreshToken());

        assertThat(result).isEqualTo(expectedTokenInfo.accessToken());
    }

    @Test
    @DisplayName("토큰 재발급 시, 리프레시 토큰으로 인증정보를 얻어내지 못했다면 에러를 던진다")
    void no_valid_refresh_token_reissue_throw_TokenNotFoundException() {
        TokenInfo tokenInfo = new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN");

        given(redisRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () ->
                tokenService.reissueToken(tokenInfo.refreshToken()));
    }

    @Test
    @DisplayName("유효한 어세스 토큰을 받으면 인증 정보를 얻어낸다")
    void valid_access_token_create_user_principal() {
        UserPrincipal principal = new UserPrincipal(1L, "devteller123@gmail.com");
        String accessToken = "ACCESS_TOKEN";

        given(jwtProvider.verifyToken(any())).willReturn(principal);

        UserPrincipal result = tokenService.getUserPrincipal(accessToken);

        assertThat(result.getName()).isEqualTo(expectedPrincipal.getName());
        assertThat(result.getEmail()).isEqualTo(expectedPrincipal.getEmail());
    }
}
