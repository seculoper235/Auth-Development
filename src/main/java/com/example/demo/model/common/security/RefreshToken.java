package com.example.demo.model.common.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "Auth", timeToLive = 86400000)
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String refreshToken;

    private UserPrincipal userPrincipal;
}
