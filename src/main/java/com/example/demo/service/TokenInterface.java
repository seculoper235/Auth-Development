package com.example.demo.service;

import com.example.demo.model.common.security.TokenInfo;
import com.example.demo.web.exception.model.InvalidTokenException;

public interface TokenInterface<T> {
    TokenInfo createToken(T principal);

    String reissueToken(String token) throws InvalidTokenException;

    T getPrincipal(String token);
}
