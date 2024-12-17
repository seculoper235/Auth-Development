package com.example.demo.service.auth;

import com.example.demo.domain.google.GoogleClient;
import org.springframework.stereotype.Component;

@Component
public class GoogleService extends TokenClientService {
    private final GoogleClient googleClient;

    public GoogleService(GoogleClient googleClient) {
        this.googleClient = googleClient;
    }

    @Override
    public String reissue(String refreshToken) {
        return refreshToken;
    }

    public void unlink(String token) {
        googleClient.unlink(token);
    }
}
