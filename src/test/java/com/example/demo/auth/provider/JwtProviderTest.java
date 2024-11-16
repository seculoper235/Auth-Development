package com.example.demo.auth.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.config.ComponentTestEnv;
import com.example.demo.model.common.auth.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtProviderTest extends ComponentTestEnv {
    private final Long EXPIRATION_MILLISECONDS = 2 * 1000 * 60 * 30L;

    @Value("${jwt.secret}")
    String secret;

    @Autowired
    private JwtProvider jwtProvider;

    private UserPrincipal paramPrincipal;

    private UserPrincipal expectedPrincipal;

    @BeforeEach
    void setUp() {
        paramPrincipal = new UserPrincipal("dev teller", "devteller123@gmail.com");
        expectedPrincipal = new UserPrincipal("dev teller", "devteller123@gmail.com");
    }

    @Test
    @DisplayName("JWT 생성 시, 인증된 사용자 정보를 받으면 인증 정보 클레임이 있는 JWT를 반환한다")
    void user_principal_create_token_test() {
        // When
        String token = jwtProvider.createToken(paramPrincipal, EXPIRATION_MILLISECONDS);
        DecodedJWT result = JWT.decode(token);

        // Then
        assertThat(result.getIssuer()).isEqualTo("dev StoryTeller");
        assertThat(result.getSubject()).isEqualTo("dev StoryTeller");
        assertThat(result.getClaim("id").asString()).isEqualTo(expectedPrincipal.getName());
        assertThat(result.getClaim("email").asString()).isEqualTo(expectedPrincipal.getEmail());
    }

    @Test
    @DisplayName("JWT 생성 시, 값을 넘겨주지 않으면 기본 클레임만 있는 JWT를 반환한다")
    void no_param_create_token_test() {
        // When
        String token = jwtProvider.createToken(EXPIRATION_MILLISECONDS);
        DecodedJWT result = JWT.decode(token);

        // Then
        assertThat(result.getIssuer()).isEqualTo("dev StoryTeller");
        assertThat(result.getSubject()).isEqualTo("dev StoryTeller");
        assertNull(result.getClaim("id").asString());
        assertNull(result.getClaim("email").asString());
    }

    @Test
    @DisplayName("JWT 인증 시, 유효한 JWT를 받으면 사용자 정보를 반환한다")
    void valid_token_create_user_principal_test() {
        // Given
        Date now = new Date();
        Date expired = new Date(now.getTime() + EXPIRATION_MILLISECONDS);

        String accessToken = JWT.create()
                .withIssuer("dev StoryTeller")
                .withSubject("dev StoryTeller")
                .withAudience(paramPrincipal.getName())
                .withClaim("id", paramPrincipal.getName())
                .withClaim("email", paramPrincipal.getEmail())
                .withExpiresAt(expired)
                .sign(Algorithm.HMAC256(secret));

        // When
        UserPrincipal result = jwtProvider.verifyToken(accessToken);

        // Then
        assertThat(result.getName()).isEqualTo(expectedPrincipal.getName());
        assertThat(result.getEmail()).isEqualTo(expectedPrincipal.getEmail());
    }

    @Test
    @DisplayName("JWT 검증 시, 만료된 JWT를 받으면 TokenExpiredException을 반환한다")
    void expired_token_throw_token_expired_exception() {
        // Given
        Date now = new Date();
        Date expired = new Date(now.getTime() + 1L);

        String accessToken = JWT.create()
                .withIssuer("dev StoryTeller")
                .withSubject("dev StoryTeller")
                .withAudience(paramPrincipal.getName())
                .withClaim("id", paramPrincipal.getName())
                .withClaim("email", paramPrincipal.getEmail())
                .withExpiresAt(expired)
                .sign(Algorithm.HMAC256(secret));

        assertThrows(TokenExpiredException.class,
                () -> jwtProvider.verifyToken(accessToken));
    }

    @Test
    @DisplayName("JWT 검증 시, 잘못된 JWT를 받으면 JWTVerificationException을 반환한다")
    void no_valid_token_throw_jwt_verification_exception() {
        String accessToken = "NO_VALID_ACCESS_TOKEN";

        assertThrows(JWTVerificationException.class,
                () -> jwtProvider.verifyToken(accessToken));
    }
}
