package com.example.demo.auth.service;

import com.example.demo.config.ServiceTestEnv;
import com.example.demo.model.common.auth.AuthUser;
import com.example.demo.persistence.AuthUserRepository;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.auth.AuthUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class AuthUserServiceTest extends ServiceTestEnv {
    @InjectMocks
    private AuthUserService authUserService;

    @Mock
    private AuthUserRepository authUserRepository;

    private AuthUserInfo expected;

    @BeforeEach
    void setUp() {
        expected = new AuthUserInfo(1L, "devteller123@gmail.com");
    }

    @Test
    @DisplayName("사용자 등록에 성공하면 유저 정보를 반환한다")
    void register_user_success_return_user_api() {
        AuthUser authUser = new AuthUser(1L, "devteller123@gmail.com", "test123", Collections.emptyList());
        given(authUserRepository.save(any())).willReturn(authUser);

        AuthUserInfo result = authUserService.register(authUser);

        assertThat(result.id()).isEqualTo(expected.id());
        assertThat(result.email()).isEqualTo(expected.email());
    }
}
