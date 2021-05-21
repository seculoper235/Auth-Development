package com.example.demo.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TeamResponse {
    private int teamId;
    private String teamName;

    private List<UserDto> userList = new ArrayList<>();

    @Builder
    public TeamResponse(int teamId, String teamName, List<UserDto> userList) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.userList = userList;
    }
}
