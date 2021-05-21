package com.example.demo.Service;

import com.example.demo.Domain.Team;
import com.example.demo.Domain.User;
import com.example.demo.Entity.TeamDto;
import com.example.demo.Entity.UserDto;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {
        User newUser = User.builder()
                .name(userDto.getName())
                .description(userDto.getDescription())
                .team(Team.builder()
                            .id(userDto.getTeam().getTeamId())
                            .name(userDto.getTeam().getTeamName())
                            .build())
                .build();
        User user = userRepository.save(newUser);
        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .description(user.getDescription())
                .team(TeamDto.builder()
                        .teamId(user.getTeam().getId())
                        .teamName(user.getTeam().getName())
                        .build())
                .build();
    }

    public UserDto selectUser(int id) {
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .description(user.getDescription())
                .team(TeamDto.builder()
                        .teamId(user.getTeam().getId())
                        .teamName(user.getTeam().getName())
                        .build())
                .build();
    }

    public UserDto updateUser(UserDto userDto) {
        User newUser = User.builder()
                .id(userDto.getUserId())
                .name(userDto.getName())
                .description(userDto.getDescription())
                .team(Team.builder()
                        .id(userDto.getTeam().getTeamId())
                        .name(userDto.getTeam().getTeamName())
                        .build())
                .build();
        User user = userRepository.save(newUser);
        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .description(user.getDescription())
                .team(TeamDto.builder()
                        .teamId(user.getTeam().getId())
                        .teamName(user.getTeam().getName())
                        .build())
                .build();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
