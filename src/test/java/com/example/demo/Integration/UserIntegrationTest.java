package com.example.demo.Integration;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserIntegrationTest extends UserIntegrationConfig {
    @DisplayName("유저 조회 테스트")
    @Test
    public void selectUserTest() throws Exception {
        // Mock API와 동일하다. 다만, 더이상 가짜인 MockBean이 아니기 때문에 given이 필요없다. 그냥 mvc를 이용해 실행하면 된다.
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo(testUser.getId())))
                .andExpect(jsonPath("$.name", equalTo(testUser.getName())))
                .andExpect(jsonPath("$.description", equalTo(testUser.getDescription())))
                .andExpect(jsonPath("$.team.teamId", equalTo(testUser.getTeam().getId())))
                .andExpect(jsonPath("$.team.teamName", equalTo(testUser.getTeam().getName())));
    }
}
