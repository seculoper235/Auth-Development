package com.example.demo.auth.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.domain.JwtProvider;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.web.security.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.web.exception.model.ExceptionStatus.TOKEN_INVALID;
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
        })
public class JwtFilterTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    JwtProvider jwtProvider;

    @Test
    @DisplayName("Secured API 요청 시, 헤더에 주어진 토큰으로 인증에 성공했다면 정보를 반환한다")
    void get_user_info_provide_authenticated_token_then_return_user_info() throws Exception {
        String validToken = "VALID_TOKEN";
        UserPrincipal userPrincipal = new UserPrincipal(1L, "devteller123@gmail.com");

        given(jwtProvider.verifyToken(any())).willReturn(userPrincipal);

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Secured API 요청 시, 헤더에 토큰이 주어지지 않았다면 UnAuthorized 에러 정보를 던진다")
    void get_user_info_not_provide_token_then_return_unauthorized() throws Exception {

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(TOKEN_INVALID.getCode())));
    }

    @Test
    @DisplayName("Secured API 요청 시, 주어진 토큰으로 인증에 실패했다면 UnAuthorized 에러를 던진다")
    void get_user_info_provide_invalid_token_then_return_unauthorized() throws Exception {
        String invalidToken = "INVALID_TOKEN";

        given(jwtProvider.verifyToken(invalidToken))
                .willThrow(new JWTVerificationException("올바르지 않은 토큰입니다"));

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(TOKEN_INVALID.getCode())));
    }

}
