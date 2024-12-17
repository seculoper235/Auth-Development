package com.example.demo.service.auth;

import com.example.demo.infra.AuthUserRepository;
import com.example.demo.infra.SnsAccountRepository;
import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.model.common.auth.SnsAccount;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import com.example.demo.web.exception.model.DuplicatedEntityException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUserRepository authUserRepository;
    private final SnsAccountRepository snsAccountRepository;
    private final ClientServiceProvider clientServiceProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SnsAccount findSnsAccount(String uid) throws CredentialNotMatchException {

        return snsAccountRepository.findByUid(uid)
                .orElseThrow(CredentialNotMatchException::new);
    }

    public AuthUserInfo find(Long id) throws CredentialNotMatchException {

        return authUserRepository.findById(id)
                .map(AuthUser::toInfo)
                .orElseThrow(CredentialNotMatchException::new);
    }

    public AuthUserInfo register(AuthUser user) {

        return authUserRepository.save(user).toInfo();
    }

    public SnsAccountInfo register(String email, SnsAccount snsAccount) throws DuplicatedEntityException {
        AuthUser authUser = authUserRepository.getByEmail(email);

        boolean isDuplicated = authUser.getSnsAccounts().stream()
                .anyMatch(account -> account.getType().equals(snsAccount.getType()));

        if (isDuplicated) {
            throw new DuplicatedEntityException("이미 연동 계정이 존재합니다.");
        }

        SnsAccount param = SnsAccount.builder()
                .uid(snsAccount.getUid())
                .type(snsAccount.getType())
                .refreshToken(snsAccount.getRefreshToken())
                .authUser(authUser)
                .build();

        SnsAccount result = snsAccountRepository.save(param);

        return result.toInfo();
    }

    @Transactional
    public void deregister(String email, SnsAccount snsAccount) {
        // Provider와 연동 해제
        ClientService clientService = clientServiceProvider.getClientService(snsAccount.getType());

        if (clientService instanceof AdminKeyClientService) {
            ((AdminKeyClientService) clientService).unlink(snsAccount.getUid());
        } else if (clientService instanceof TokenClientService) {
            String accessToken = ((TokenClientService) clientService).reissue(snsAccount.getRefreshToken());
            ((TokenClientService) clientService).unlink(accessToken);
        }

        AuthUser authUser = authUserRepository.getByEmail(email);

        boolean isMatchAccount = authUser.getSnsAccounts().stream()
                .anyMatch(account -> account.getUid().equals(snsAccount.getUid()));

        if (!isMatchAccount) {
            throw new EntityNotFoundException("연동된 계정이 없습니다.");
        }

        snsAccountRepository.deleteByUid(snsAccount.getUid());
    }

    public UserPrincipal authenticate(String email, String password) throws CredentialNotMatchException {
        return authUserRepository.findByEmail(email)
                .filter(entity -> entity.matchPassword(passwordEncoder, password))
                .map(user -> new UserPrincipal(user.getId(), user.getEmail()))
                .orElseThrow(CredentialNotMatchException::new);
    }

    public UserPrincipal authenticate(String uid) {
        AuthUser authUser = snsAccountRepository.findByUid(uid)
                .map(SnsAccount::getAuthUser)
                .orElseThrow(() -> new EntityNotFoundException("연동된 계정이 없습니다."));

        return new UserPrincipal(authUser.getId(), authUser.getEmail());
    }
}
