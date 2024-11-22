package com.example.demo.service.token;

import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.token.RefreshToken;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.persistence.RedisRepository;
import com.example.demo.web.exception.model.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService implements TokenInterface<UserPrincipal> {
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

    public String reissueToken(String token) throws InvalidTokenException {
        Optional<RefreshToken> refreshToken = redisRepository.findById(token);

        if (refreshToken.isPresent()) {
            return jwtProvider.createToken(
                    refreshToken.get().getUserPrincipal(),
                    EXPIRATION_MILLISECONDS);
        } else {
            throw new InvalidTokenException("올바르지 않은 토큰입니다");
        }
    }

    public UserPrincipal getPrincipal(String token) {
        return jwtProvider.verifyToken(token);
    }

    public void deletePrincipal(String refreshToken) {
        redisRepository.deleteById(refreshToken);
    }
}
