package com.example.demo.web.security.config;

import java.util.Map;

public class GoogleOAuthInfo extends OAuthInfo {
    public GoogleOAuthInfo(Map<String, Object> attributes) {
        super(attributes, "google");
    }
}
