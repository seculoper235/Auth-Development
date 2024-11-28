package com.example.demo.auth.controller;


import com.example.demo.model.common.AppUser;
import com.example.demo.service.app.AppUserService;
import com.example.demo.web.controller.user.AppUserController;
import com.example.demo.web.exception.model.ExceptionStatus;
import com.example.demo.web.security.JwtFilter;
import com.example.demo.web.security.config.SecurityConfig;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(value = AppUserController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class}),
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JwtFilter.class}),
        })
public class AppUserControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AppUserService userService;

    @Test
    @WithMockUser(username = "dev teller")
    @DisplayName("사용자 조회 시에 사용자가 존재한다면 사용자 정보를 반환한다")
    void find_user_exist_user_return_user_info() throws Exception {
        AppUser expected = new AppUser(1L, "dev teller");

        given(userService.find(any())).willReturn(expected);

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(expected.getId().intValue())))
                .andExpect(jsonPath("$.name", equalTo(expected.getName())));
    }

    @Test
    @WithMockUser(username = "dev teller")
    @DisplayName("사용자 조회 시에 사용자가 없다면 404 에러를 반환한다")
    void find_user_not_exist_user_return_http_404() throws Exception {

        given(userService.find(any()))
                .willThrow(new EntityNotFoundException("존재하지 않는 사용자입니다."));

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", equalTo(ExceptionStatus.ENTITY_NOT_FOUND.getCode())));
    }
}
