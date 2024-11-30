package com.example.demo.auth.controller;

import com.example.demo.model.common.auth.SnsType;
import com.example.demo.model.common.token.UserPrincipal;
import com.example.demo.service.auth.AuthService;
import com.example.demo.service.auth.AuthUserInfo;
import com.example.demo.service.token.TokenInfo;
import com.example.demo.service.token.TokenService;
import com.example.demo.web.controller.auth.AuthController;
import com.example.demo.web.controller.auth.LoginRequest;
import com.example.demo.web.controller.auth.LoginResponse;
import com.example.demo.web.controller.auth.SignupRequest;
import com.example.demo.web.exception.model.CredentialNotMatchException;
import com.example.demo.web.exception.model.ExceptionStatus;
import com.example.demo.web.exception.model.InvalidTokenException;
import com.example.demo.web.security.JwtFilter;
import com.example.demo.web.security.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(value = AuthController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class}),
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {JwtFilter.class}),
        })
public class AuthControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AuthService userService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    protected ObjectMapper objectMapper;

    private LoginResponse expected;

    @BeforeEach
    public void setUp() {
        expected = new LoginResponse(1L, "devteller123@gmail.com", new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN"));
    }

    @Test
    @DisplayName("사용자 등록 시에 등록이 완료되면 Location 헤더에 사용자 API가 담긴다")
    void register_user_success_location_header_user_api() throws Exception {
        URI expected = URI.create("/api/user/" + 2);

        SignupRequest request = new SignupRequest("dev teller2", "devteller456@gmail.com", "test456!");
        AuthUserInfo authUserInfo = new AuthUserInfo(2L, "dev teller2", "devteller456@gmail.com", Collections.emptyList());

        given(userService.register(any())).willReturn(authUserInfo);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", equalTo(expected.toString())));
    }

    @Test
    @DisplayName("로그인 할 때, 인증이 완료되면 LoginResponse를 반환한다")
    public void login_user_authenticated_login_response() throws Exception {
        LoginRequest request = new LoginRequest("devteller123@gmail.com", "test123!");

        UserPrincipal principal = new UserPrincipal(1L, "devteller123@gmail.com");
        TokenInfo tokenInfo = new TokenInfo("ACCESS_TOKEN", "REFRESH_TOKEN");

        given(userService.authenticate(any(), any())).willReturn(principal);
        given(tokenService.createToken(principal)).willReturn(tokenInfo);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(expected.id().intValue())))
                .andExpect(jsonPath("$.email", equalTo(expected.email())))
                .andExpect(jsonPath("$.tokenInfo.accessToken", notNullValue()))
                .andExpect(jsonPath("$.tokenInfo.refreshToken", notNullValue()));
    }

    @Test
    @DisplayName("로그인 할 때, 사용자가 없으면 에러를 던진다")
    void login_not_found_user_throws_entity_not_found_exception() throws Exception {
        LoginRequest request = new LoginRequest("guest", "test123!");

        given(userService.authenticate(any(), any())).willThrow(new CredentialNotMatchException());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(ExceptionStatus.CREDENTIAL_NOT_MATCH.getCode())));
    }

    @Test
    @DisplayName("로그인 할 때, 비밀번호가 일치하지 않으면 에러를 던진다")
    void login_mismatch_password_throws_mismatch_password_exception() throws Exception {
        LoginRequest request = new LoginRequest("devteller123@gmail.com", "guest123!");

        given(userService.authenticate(any(), any())).willThrow(new CredentialNotMatchException());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 로그인 시, 해당하는 SNS 연동이 되어있으면 LoginResponse를 반환한다")
    void sns_login_exist_link_sns_return_login_response(SnsType snsType) throws Exception {
    }

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 로그인 시, 사용자가 존재하지 않으면 401 에러가 발생한다")
    void sns_login_not_found_user_return_unauthorized(SnsType snsType) throws Exception {
    }

    @ParameterizedTest
    @EnumSource(SnsType.class)
    @DisplayName("SNS 로그인 시, 해당하는 SNS 연동이 되어있지 않으면 401 에러가 발생한다")
    void sns_login_not_exist_link_sns_return_unauthorized(SnsType snsType) throws Exception {
    }

    @Test
    @DisplayName("토큰 재발급 할 때, DB에 토큰 정보가 있다면 어세스 토큰을 발급한다")
    void reissue_found_refresh_token_issue_access_token() throws Exception {
        String request = "REFRESH_TOKEN";
        String result = "ACCESS_TOKEN";

        given(tokenService.reissueToken(any())).willReturn(result);

        mockMvc.perform(post("/api/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()));
    }

    @Test
    @DisplayName("토큰 재발급 할 때, DB에 토큰 정보가 없다면 에러를 던진다")
    void reissue_not_found_refresh_token_throws_exception() throws Exception {
        String request = "INVALID_REFRESH_TOKEN";

        given(tokenService.reissueToken(any())).willThrow(new InvalidTokenException("올바르지 않은 토큰입니다"));

        mockMvc.perform(post("/api/auth/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(ExceptionStatus.TOKEN_INVALID.getCode())));
    }

    @Test
    @WithMockUser(username = "dev teller")
    @DisplayName("로그아웃 할 때, No Content를 반환한다")
    void logout_no_content() throws Exception {
        String request = "REFRESH_TOKEN";

        mockMvc.perform(delete("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNoContent());
    }
}
