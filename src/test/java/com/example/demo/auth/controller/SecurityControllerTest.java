package com.example.demo.auth.controller;

import com.example.demo.config.ControllerTestEnv;
import com.example.demo.model.common.security.RefreshToken;
import com.example.demo.model.common.security.TokenInfo;
import com.example.demo.model.common.security.UserPrincipal;
import com.example.demo.persistence.RedisRepository;
import com.example.demo.web.controller.auth.LoginRequest;
import com.example.demo.web.controller.auth.LoginResponse;
import com.example.demo.web.exception.domain.ExceptionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityControllerTest extends ControllerTestEnv {

    @Autowired
    private RedisRepository repository;

    private LoginResponse expected;

    @BeforeEach
    public void setUp() {
        expected = new LoginResponse(1L, "devteller123@gmail.com", new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN"));

        UserPrincipal principal = new UserPrincipal(1L, "devteller123@gmail.com");
        RefreshToken refreshToken = new RefreshToken("REFRESH_TOKEN", principal);

        repository.save(refreshToken);
    }

    @Test
    @DisplayName("로그인 할 때, 인증이 완료되면 LoginResponse를 반환한다")
    public void login_user_authenticated_login_response() throws Exception {
        LoginRequest param = new LoginRequest("devteller123@gmail.com", "test123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(expected.id().intValue())))
                .andExpect(jsonPath("$.email", equalTo(expected.email())))
                .andExpect(jsonPath("$.tokenInfo.accessToken", notNullValue()))
                .andExpect(jsonPath("$.tokenInfo.refreshToken", notNullValue()));
    }

    @Test
    @DisplayName("로그인 할 때, 사용자가 없으면 에러를 던진다")
    void login_not_found_user_throws_entity_not_found_exception() throws Exception {
        LoginRequest param = new LoginRequest("guest", "test123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(ExceptionStatus.CREDENTIAL_NOT_MATCH.getCode())));
    }

    @Test
    @DisplayName("로그인 할 때, 비밀번호가 일치하지 않으면 에러를 던진다")
    void login_mismatch_password_throws_mismatch_password_exception() throws Exception {
        LoginRequest param = new LoginRequest("devteller123@gmail.com", "guest123!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("토큰 재발급 할 때, DB에 토큰 정보가 있다면 어세스 토큰을 발급한다")
    void reissue_found_refresh_token_issue_access_token() throws Exception {
        String param = "REFRESH_TOKEN";

        mockMvc.perform(post("/api/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()));
    }

    @Test
    @DisplayName("토큰 재발급 할 때, DB에 토큰 정보가 없다면 에러를 던진다")
    void reissue_not_found_refresh_token_throws_exception() throws Exception {
        String param = "INVALID_REFRESH_TOKEN";

        mockMvc.perform(post("/api/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(ExceptionStatus.TOKEN_NOT_FOUND.getCode())));
    }

    @Test
    @DisplayName("로그아웃 할 때, No Content를 반환한다")
    void logout_no_content() throws Exception {
        String param = "REFRESH_TOKEN";

        mockMvc.perform(delete("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andExpect(status().isNoContent());
    }
}
