package com.example.demo.service.auth;

import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.model.common.auth.SnsAccount;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.persistence.AuthUserRepository;
import com.example.demo.persistence.SnsAccountRepository;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final SnsAccountRepository snsAccountRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthUserInfo find(Long id) throws CredentialNotMatchException {
        AuthUser authUser = authUserRepository.findById(id)
                .orElseThrow(CredentialNotMatchException::new);

        return new AuthUserInfo(authUser.getId(), authUser.getName(), authUser.getEmail(), authUser.getSnsAccounts());
    }

    public AuthUserInfo register(AuthUser user) {
        AuthUser authUser = authUserRepository.save(user);

        return new AuthUserInfo(authUser.getId(), authUser.getName(), authUser.getEmail(), authUser.getSnsAccounts());
    }

    public SnsAccount register(String email, SnsAccount snsAccount) throws CredentialNotMatchException {
        AuthUser authUser = authUserRepository.findByEmail(email)
                .orElseThrow(CredentialNotMatchException::new);

        SnsAccount param = SnsAccount.builder()
                .uid(snsAccount.getUid())
                .type(snsAccount.getType())
                .authUser(authUser)
                .build();

        return snsAccountRepository.save(param);
    }

    public UserPrincipal authenticate(String email, String password) throws CredentialNotMatchException {
        return authUserRepository.findByEmail(email)
                .filter(entity -> entity.matchPassword(passwordEncoder, password))
                .map(user -> new UserPrincipal(user.getId(), user.getEmail()))
                .orElseThrow(CredentialNotMatchException::new);
    }

    public UserPrincipal authenticate(String uid) throws CredentialNotMatchException {
        Optional<SnsAccount> snsAccount = snsAccountRepository.findByUid(uid);

        AuthUser authUser = snsAccount.map(SnsAccount::getAuthUser)
                .orElseThrow(CredentialNotMatchException::new);

        return new UserPrincipal(authUser.getId(), authUser.getEmail());
    }
}
