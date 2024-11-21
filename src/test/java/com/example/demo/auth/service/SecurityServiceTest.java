package com.example.demo.auth.service;

import com.example.demo.config.ServiceTestEnv;
import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.model.common.security.UserPrincipal;
import com.example.demo.persistence.AuthUserRepository;
import com.example.demo.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class SecurityServiceTest extends ServiceTestEnv {
    @InjectMocks
    private SecurityService securityService;

    @Mock
    private AuthUserRepository authUserRepository;

    private UserPrincipal expected;

    @BeforeEach
    void setUp() {
        expected = new UserPrincipal(1L, "devteller123@gmail.com");
    }

    @Test
    @DisplayName("이메일로 로그인 시, ID / PW가 일치한다면 인증 정보를 발급한다")
    void email_login_match_password_create_principal() {
        String id = "devteller123@gmail.com";
        String password = "test123!";

        AuthUser authUser = AuthUser.builder()
                .id(1L)
                .email("devteller123@gmail.com")
                .password("$2a$10$B5tKf/PyHEzhpImNkwHKyONoPyqCcXY68DVLQB6PIGsZARUrsGxdq")
                .build();

        given(authUserRepository.findByEmail(any())).willReturn(Optional.of(authUser));

        UserPrincipal result = securityService.authenticate(id, password);

        assertThat(result.getName()).isEqualTo(expected.getName());
        assertThat(result.getEmail()).isEqualTo(expected.getEmail());
    }

    @Test
    @DisplayName("이메일로 로그인 시, ID / PW가 일치하지 않는다면 에러를 던진다")
    void email_login_mismatch_password_throw_exception() {
        String email = "devteller123@gmail.com";
        String password = "test123!";

        given(authUserRepository.findByEmail(any())).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                securityService.authenticate(email, password));
    }
}
