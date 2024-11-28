package com.example.demo.service.auth;

import com.example.demo.model.common.auth.SnsAccount;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
public record AuthUserInfo(
        Long id,
        String name,
        String email,
        List<SnsAccount> snsAccounts
) {}
