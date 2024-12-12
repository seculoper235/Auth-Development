package com.example.demo.auth.service;

import com.example.demo.config.UnitTest;
import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.infra.AuthUserRepository;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class AuthServiceTest extends UnitTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthUserRepository authUserRepository;

    private UserPrincipal expectedPrincipal;

    private AuthUserInfo expectedUser;

    @BeforeEach
    void setUp() {
        expectedPrincipal = new UserPrincipal(1L, "devteller123@gmail.com");
        expectedUser = new AuthUserInfo(1L, "dev teller", "devteller123@gmail.com", Collections.emptyList());
    }

    @Test
    @DisplayName("사용자 등록에 성공하면 유저 정보를 반환한다")
    void signup_user_success_return_user_api() {
        AuthUser authUser = new AuthUser(1L, "dev teller", "devteller123@gmail.com", "test123", Collections.emptyList());
        given(authUserRepository.save(any())).willReturn(authUser);

        AuthUserInfo result = authService.register(authUser);

        assertThat(result.id()).isEqualTo(expectedUser.id());
        assertThat(result.name()).isEqualTo(expectedUser.name());
        assertThat(result.email()).isEqualTo(expectedUser.email());
        assertThat(result.snsAccounts()).isEqualTo(expectedUser.snsAccounts());
    }

    @Test
    @DisplayName("이메일로 로그인 시, ID / PW가 일치한다면 인증 정보를 발급한다")
    void email_login_match_password_create_principal() throws CredentialNotMatchException {
        String email = "devteller123@gmail.com";
        String password = "test123!";

        AuthUser authUser = AuthUser.builder()
                .id(1L)
                .email("devteller123@gmail.com")
                .password("$2a$10$B5tKf/PyHEzhpImNkwHKyONoPyqCcXY68DVLQB6PIGsZARUrsGxdq")
                .build();

        given(authUserRepository.findByEmail(any())).willReturn(Optional.of(authUser));

        UserPrincipal result = authService.authenticate(email, password);

        assertThat(result.getName()).isEqualTo(expectedPrincipal.getName());
        assertThat(result.getEmail()).isEqualTo(expectedPrincipal.getEmail());
    }

    @Test
    @DisplayName("이메일로 로그인 시, ID / PW가 일치하지 않는다면 에러를 던진다")
    void email_login_mismatch_password_throw_exception() {
        String email = "devteller123@gmail.com";
        String password = "test123!";

        given(authUserRepository.findByEmail(any())).willReturn(Optional.empty());

        assertThrows(CredentialNotMatchException.class, () ->
                authService.authenticate(email, password));
    }
}
