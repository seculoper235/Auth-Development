package com.example.demo.service.auth;

import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.persistence.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;

    public AuthUserInfo register(AuthUser user) {
        AuthUser authUser = authUserRepository.save(user);

        return new AuthUserInfo(authUser.getId(), authUser.getEmail());
    }
}
