package com.example.demo.web.security.oauth;

import java.util.Map;

public class NaverOAuthInfo extends OAuthInfo {
    public NaverOAuthInfo(Map<String, Object> attributes) {
        super(attributes, "naver");
    }

    public String getId() {
        return getAttributes().get("id").toString();
    }
}
