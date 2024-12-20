package com.example.demo.service.token;

import com.example.demo.domain.JwtProvider;
import com.example.demo.infra.RedisRepository;
import com.example.demo.model.common.token.RefreshToken;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.web.exception.model.InvalidTokenException;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public Either<InvalidTokenException, String> reissueToken(String token) {

        return Option.ofOptional(redisRepository.findById(token))
                .map(value -> jwtProvider.createToken(
                        value.getUserPrincipal(),
                        EXPIRATION_MILLISECONDS))
                .toEither(() -> new InvalidTokenException("올바르지 않은 토큰입니다"));
    }

    public UserPrincipal getPrincipal(String token) {
        return jwtProvider.verifyToken(token);
    }

    public void deletePrincipal(String refreshToken) {
        redisRepository.deleteById(refreshToken);
    }
}
