package com.example.demo.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private int userId;
    private String name;
    private String description;

    private TeamDto team;

    @Builder
    public UserDto(int userId, String name, String description, TeamDto team) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.team = team;
    }
}
