package com.example.demo.auth.controller;

import com.example.demo.config.ControllerTestEnv;
import com.example.demo.web.controller.user.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.net.URI;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthUserControllerTest extends ControllerTestEnv {

    private URI expected;

    @BeforeEach
    public void setUp() {
        expected = URI.create("/api/user/" + 2);
    }

    @Test
    @DisplayName("사용자 등록 시에 등록이 완료되면 Location 헤더에 사용자 API가 담긴다")
    void register_user_success_location_header_user_api() throws Exception {
        UserRequest param = new UserRequest("dev teller", "devteller456@gmail.com", "test456!");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", equalTo(expected.toString())));
    }
}
