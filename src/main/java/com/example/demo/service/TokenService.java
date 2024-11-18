package com.example.demo.service;

import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.auth.AppPrincipal;
import com.example.demo.model.common.auth.TokenInfo;
import com.example.demo.model.common.auth.UserPrincipal;
import com.example.demo.persistence.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.EXPIRATION_MILLISECONDS}")
    private Long EXPIRATION_MILLISECONDS;

    @Value("${jwt.REFRESH_EXPIRATION_MILLISECONDS}")
    private Long REFRESH_EXPIRATION_MILLISECONDS;

    private final JwtProvider jwtProvider;

    private final RedisRepository redisRepository;

    public TokenInfo createToken(UserPrincipal principal) {
        String access = jwtProvider.createToken(principal, EXPIRATION_MILLISECONDS);
        String refresh = jwtProvider.createToken(REFRESH_EXPIRATION_MILLISECONDS);

        return new TokenInfo(access, refresh);
    }

    public String reissueToken(String refreshToken) {
        Optional<AppPrincipal> appPrincipal = redisRepository.findById(refreshToken);

        if (appPrincipal.isPresent()) {
            return jwtProvider.createToken(
                    appPrincipal.get().getUserPrincipal(),
                    EXPIRATION_MILLISECONDS);
        } else {
            throw new RuntimeException("토큰이 존재하지 않습니다");
        }
    }

    public UserPrincipal getUserPrincipal(String accessToken) {
        return jwtProvider.verifyToken(accessToken);
    }
}
