package com.example.demo.domain.common.auth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@JsonSerialize
@RequiredArgsConstructor
public class UserPrincipal implements Principal {

    private final String name;

    @Getter
    private final String email;

    @Override
    public String getName() {
        return this.name;
    }
}
