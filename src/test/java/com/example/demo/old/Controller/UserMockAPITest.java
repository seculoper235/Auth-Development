package com.example.demo.old.Controller;


import com.example.demo.Domain.Team;
import com.example.demo.Domain.User;
import com.example.demo.Entity.TeamDto;
import com.example.demo.Entity.UserDto;
import com.example.demo.Service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserMockAPITest extends UserMockAPIConfig {
    // Mock API도 통합 테스트와 마찬가지로 MockMvc를 사용한다.
    // 다만 차이점은 모든걸 등록하는 통합 테스트와는 달리, MockBean을 사용하여 원하는 Bean만을 등록시킨다는 것이다.
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User test;

    @Before
    public void dataSetUp() {
        Team team = Team.builder()
                .id(1)
                .name("팀1")
                .build();

        test = User.builder()
                .id(1)
                .name("유저1")
                .description("유저1 입니다.")
                .team(team)
                .build();
    }

    @DisplayName("유저 조회 테스트")
    @Test
    public void selectUserTest() throws Exception {
        TeamDto teamDto = TeamDto.builder()
                .teamId(test.getTeam().getId())
                .teamName(test.getTeam().getName())
                .build();
        UserDto userDto = UserDto.builder()
                .userId(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .team(teamDto)
                .build();

        given(userService.selectUser(anyInt())).willReturn(userDto);

        // perform으로 원하는 메소드를 수행하고, andExpect()로 비교한다. When과 Then이 같이 있는 셈이다.
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("유저1")))
                .andExpect(jsonPath("$.description", equalTo("유저1 입니다.")))
                .andExpect(jsonPath("$.team.teamId", equalTo(1)))
                .andExpect(jsonPath("$.team.teamName", equalTo("팀1")));
    }
}
