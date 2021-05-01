package com.example.demo.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "team")
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<User> users = new ArrayList<>();

    @Builder
    public Team(int id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}
