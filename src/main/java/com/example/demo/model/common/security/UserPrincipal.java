package com.example.demo.model.common.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;

@JsonSerialize
@RequiredArgsConstructor
public class UserPrincipal implements Principal {

    private final Long id;

    @Getter
    private final String email;

    @Override
    public String getName() {
        return this.id.toString();
    }
}
