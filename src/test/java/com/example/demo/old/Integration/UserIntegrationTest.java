package com.example.demo.old.Integration;

import com.example.demo.Domain.Team;
import com.example.demo.Entity.TeamDto;
import com.example.demo.Entity.UserDto;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @DisplayName("유저 생성 테스트")
    @Test
    public void createUserTest() throws Exception {
        Team team = testUser.getTeam();
        TeamDto teamDto = TeamDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
        UserDto userDto = UserDto.builder()
                .name("유저2")
                .description("유저2 입니다.")
                .team(teamDto)
                .build();

        // 현재 요청의 미디어 타입을 json으로 지정하고, ObjectMapper로 갹체를 문자열로 바꾼다.
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(userDto.getName())))
                .andExpect(jsonPath("$.description", equalTo(userDto.getDescription())))
                .andExpect(jsonPath("$.team.teamId", equalTo(teamDto.getTeamId())))
                .andExpect(jsonPath("$.team.teamName", equalTo(teamDto.getTeamName())))
                .andDo(print());
    }
}
