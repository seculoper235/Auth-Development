package com.example.demo.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamDto {
    private int teamId;
    private String teamName;

    @Builder
    public TeamDto(int teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
