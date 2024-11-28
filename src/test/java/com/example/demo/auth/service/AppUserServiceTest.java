package com.example.demo.auth.service;

import com.example.demo.config.UnitTest;
import com.example.demo.model.common.AppUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class AppUserServiceTest extends UnitTest {
    @InjectMocks
    private AppUserService appUserService;

    @Mock
    private AppUserRepository appUserRepository;

    private final AppUser expected = new AppUser(1L, "dev teller");

    @Test
    @DisplayName("사용자 조회 시, 사용자가 존재하면 유저 정보를 반환한다")
    void find_user_exist_return_user_info() {
        Long userId = 1L;

        AppUser appUser = new AppUser(1L, "dev teller");
        given(appUserRepository.findById(any())).willReturn(Optional.of(appUser));

        AppUser result = appUserService.find(userId);

        assertThat(result.getId()).isEqualTo(expected.getId());
        assertThat(result.getName()).isEqualTo(expected.getName());
    }

    @Test
    @DisplayName("사용자 조회 시, 사용자가 존재하지 않으면 예외를 던진다")
    void find_user_not_exist_throw_exception() {
        given(appUserRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                appUserService.find(any()));

    }
}
