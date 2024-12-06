package com.example.demo.web.security.config;

import java.util.Map;

public class KakaoOAuthInfo extends OAuthInfo {
    public KakaoOAuthInfo(Map<String, Object> attributes) {
        super(attributes, "kakao");
    }
}
