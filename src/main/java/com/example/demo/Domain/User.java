package com.example.demo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public User(int id, String name, String description, Team team) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.team = team;
    }
}
