package com.example.demo.domain.common.auth;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Table(name = "\"User\"")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @JoinColumn
    private List<SnsAccount> snsAccounts = Collections.emptyList();
}
