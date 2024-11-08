package com.example.demo.security;

import com.example.demo.config.SecurityConfig;
import com.example.demo.config.SecurityTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
public class SecurityConfigTest extends SecurityTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Spring Security 세션 생성 정책 테스트(STATELESS)")
    @Test
    public void session_policy_stateless_test() throws Exception {

        mockMvc.perform(formLogin())
                .andDo(print())
                .andExpect((result) -> assertNull(result.getRequest().getSession(false)));
    }

    @Test
    @DisplayName("CORS 설정(Origin, Method) 확인 테스트")
    void cors_origin_and_method_test() {
        assertDoesNotThrow(() -> {
            List<String> allowedOrigins = List.of("http://localhost:5173");

            List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS");

            mockMvc.perform(
                            options("/login")
                                    .header("Origin", allowedOrigins)
                                    .header("Access-Control-Request-Method", "POST")
                    )
                    .andExpect(status().isOk())
                    .andExpect(header().stringValues("Access-Control-Allow-Origin", allowedOrigins.toArray(new String[]{})))
                    .andExpect(header().string("Access-Control-Allow-Methods", String.join(",", allowedMethods)));
        });
    }
}
