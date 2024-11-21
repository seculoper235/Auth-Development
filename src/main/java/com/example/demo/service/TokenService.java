package com.example.demo.service;

import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.security.RefreshToken;
import com.example.demo.model.common.security.TokenInfo;
import com.example.demo.model.common.security.UserPrincipal;
import com.example.demo.persistence.RedisRepository;
import com.example.demo.web.exception.model.TokenNotFoundException;
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

        RefreshToken refreshToken = new RefreshToken(refresh, principal);
        redisRepository.save(refreshToken);

        return new TokenInfo(access, refresh);
    }

    public String reissueToken(String refreshToken) throws TokenNotFoundException {
        Optional<RefreshToken> appPrincipal = redisRepository.findById(refreshToken);

        if (appPrincipal.isPresent()) {
            return jwtProvider.createToken(
                    appPrincipal.get().getUserPrincipal(),
                    EXPIRATION_MILLISECONDS);
        } else {
            throw new TokenNotFoundException("토큰이 존재하지 않습니다");
        }
    }

    public UserPrincipal getUserPrincipal(String accessToken) {
        return jwtProvider.verifyToken(accessToken);
    }

    public void deleteUserPrincipal(String refreshToken) {
        redisRepository.deleteById(refreshToken);
    }
}
