package com.example.demo.auth.persistence;

import com.example.demo.config.JpaIntegrationTest;
import com.example.demo.infra.AppUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppUserTest extends JpaIntegrationTest {
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("아이디로 사용자 조회 시, 데이터가 존재한다면 Optional.isPresent()를 만족한다")
    void find_by_id_exist_return_optional_is_present() {
        Long userId = 1L;

        assertTrue(appUserRepository.findById(userId).isPresent());
    }

    @Test
    @DisplayName("아이디로 사용자 조회 시, 데이터가 존재하지 않는다면 Optional.isPresent()를 만족하지 않는다")
    void find_by_id_not_exist_return_optional_is_empty() {
        Long userId = 2L;

        assertFalse(appUserRepository.findById(userId).isPresent());
    }
}
