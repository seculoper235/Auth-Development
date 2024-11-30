package com.example.demo.auth.controller;

import com.example.demo.model.common.auth.SnsType;
import com.example.demo.web.controller.auth.AuthUserController;
import com.example.demo.web.security.JwtFilter;
import com.example.demo.web.security.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(value = AuthUserController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class}),
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JwtFilter.class}),
        })
public class AuthUserControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 연동 시, 연동이 되어있지 않으면 Created를 반환한다")
    void sns_link_exist_not_link_return_created(SnsType snsType) throws Exception {
    }

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 연동 시, 이미 연동이 되어있으면 에러를 반환한다")
    void sns_link_exist_already_link_return_server_error(SnsType snsType) throws Exception {
    }

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 연동 해제 시, 연동이 되어있으면 No Content를 반환한다")
    void sns_unlink_exist_link_return_ok(SnsType snsType) throws Exception {
    }

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 연동 해제 시, 이미 연동이 되어있지 않으면 Not Found 에러를 반환한다")
    void sns_unlink_already_not_link_return_server_error(SnsType snsType) throws Exception {
    }
}
