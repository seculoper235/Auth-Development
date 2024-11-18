package com.example.demo.model.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("Auth")
@AllArgsConstructor
public class AppPrincipal {
    @Id
    private String refreshToken;

    private UserPrincipal userPrincipal;
}
