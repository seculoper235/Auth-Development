package com.example.demo.auth.controller;

import com.example.demo.config.ControllerTestEnv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.example.demo.web.exception.model.ExceptionStatus.TOKEN_INVALID;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtFilterTest extends ControllerTestEnv {

    @Test
    @DisplayName("Secured API 요청 시, 헤더에 주어진 토큰으로 인증에 성공했다면 정보를 반환한다")
    void get_user_info_provide_authenticated_token_then_return_user_info() throws Exception {

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
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

        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", equalTo(TOKEN_INVALID.getCode())));
    }

}
