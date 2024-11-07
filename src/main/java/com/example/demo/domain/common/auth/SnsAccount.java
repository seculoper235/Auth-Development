package com.example.demo.domain.common.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "\"SNS_ACCOUNT\"")
@Entity
public class SnsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private SnsType type;
}
