package com.example.demo.web.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public abstract class OAuthInfo {
    protected final Map<String, Object> attributes;
    protected final String registrationId;

    public String getId() {
        return attributes.get("sub").toString();
    }
}
