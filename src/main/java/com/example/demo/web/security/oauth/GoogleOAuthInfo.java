package com.example.demo.web.security.oauth;

import java.util.Map;

public class GoogleOAuthInfo extends OAuthInfo {
    public GoogleOAuthInfo(Map<String, Object> attributes) {
        super(attributes, "google");
    }
}
