package com.example.demo.service.auth;

public abstract class TokenClientService implements ClientService {
    public abstract String reissue(String refreshToken);

    public abstract void unlink(String accessToken);
}
