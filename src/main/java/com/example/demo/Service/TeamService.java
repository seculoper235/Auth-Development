package com.example.demo.Service;

import com.example.demo.Domain.Team;
import com.example.demo.Entity.TeamDto;
import com.example.demo.Entity.TeamResponse;
import com.example.demo.Entity.UserDto;
import com.example.demo.Repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamDto selectTeamByName(String name) {
        Team team = teamRepository.findTeamByName(name);
        if(team == null)
            throw new NoSuchElementException();

        return TeamDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
    }

    public List<TeamDto> selectAllTeams() {
        List<Team> teamLIst = teamRepository.findAll();
        return teamLIst.stream().map(team ->
                TeamDto.builder()
                        .teamId(team.getId())
                        .teamName(team.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public TeamDto createTeam(TeamDto teamDto) {
        Team newTeam = Team.builder()
                .id(teamDto.getTeamId())
                .name(teamDto.getTeamName())
                .build();
        Team team = teamRepository.save(newTeam);
        return TeamDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
    }

    public TeamResponse selectAllUserFromTeam(int id) {
        Team team = teamRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return TeamResponse.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .userList(team.getUsers().stream().map(user ->
                            UserDto.builder()
                                .userId(user.getId())
                                .name(user.getName())
                                .description(user.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public TeamDto updateTeam(TeamDto teamDto) {
        Team newTeam = Team.builder()
                .id(teamDto.getTeamId())
                .name(teamDto.getTeamName())
                .build();
        Team team = teamRepository.save(newTeam);
        return TeamDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
    }

    public void deleteTeam(int id) {
        teamRepository.deleteById(id);
    }
}
