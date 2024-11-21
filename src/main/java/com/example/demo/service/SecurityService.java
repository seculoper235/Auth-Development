package com.example.demo.service;

import com.example.demo.model.common.security.UserPrincipal;
import com.example.demo.persistence.AuthUserRepository;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final AuthUserRepository authUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserPrincipal authenticate(String email, String password) throws CredentialNotMatchException {
        return authUserRepository.findByEmail(email)
                .filter(entity -> entity.matchPassword(passwordEncoder, password))
                .map(user -> new UserPrincipal(user.getId(), user.getEmail()))
                .orElseThrow(CredentialNotMatchException::new);
    }
}
