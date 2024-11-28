package com.example.demo.service.auth;

import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.persistence.AuthUserRepository;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthUserInfo register(AuthUser user) {
        AuthUser authUser = authUserRepository.save(user);

        return new AuthUserInfo(authUser.getId(), authUser.getName(), authUser.getEmail(), authUser.getSnsAccounts());
    }

    public UserPrincipal authenticate(String email, String password) throws CredentialNotMatchException {
        return authUserRepository.findByEmail(email)
                .filter(entity -> entity.matchPassword(passwordEncoder, password))
                .map(user -> new UserPrincipal(user.getId(), user.getEmail()))
                .orElseThrow(CredentialNotMatchException::new);
    }
}
