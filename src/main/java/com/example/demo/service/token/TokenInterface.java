package com.example.demo.service.token;

import com.example.demo.web.exception.model.InvalidTokenException;
import io.vavr.control.Either;

public interface TokenInterface<T> {
    /**
     * 인증 정보를 가지고 토큰을 발급합니다
     */
    TokenInfo createToken(T principal);

    /**
     * 리프레시 토큰을 받아 토큰을 재발급 합니다
     */
    Either<InvalidTokenException, String> reissueToken(String token);

    /**
     * 토큰을 받아 인증 정보를 추출합니다
     */
    T getPrincipal(String token);
}
