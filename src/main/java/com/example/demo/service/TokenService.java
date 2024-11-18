package com.example.demo.service;

import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.auth.TokenInfo;
import com.example.demo.model.common.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("jwt.EXPIRATION_MILLISECONDS")
    private Long EXPIRATION_MILLISECONDS;

    @Value("jwt.REFRESH_EXPIRATION_MILLISECONDS")
    private Long REFRESH_EXPIRATION_MILLISECONDS;

    private final JwtProvider jwtProvider;

    public TokenInfo createToken(UserPrincipal principal) {
        String access = jwtProvider.createToken(principal, EXPIRATION_MILLISECONDS);
        String refresh = jwtProvider.createToken(REFRESH_EXPIRATION_MILLISECONDS);

        return new TokenInfo(access, refresh);
    }

    public UserPrincipal getUserPrincipal(String accessToken) {
        return jwtProvider.verifyToken(accessToken);
    }
}
