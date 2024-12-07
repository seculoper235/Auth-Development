package com.example.demo.web.security.oauth;

import java.util.Map;

public class KakaoOAuthInfo extends OAuthInfo {
    public KakaoOAuthInfo(Map<String, Object> attributes) {
        super(attributes, "kakao");
    }

    public String getId() {
        return getAttributes().get("id").toString();
    }
}
