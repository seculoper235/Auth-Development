package com.example.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonSerialize
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SnsAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SnsType snsType;
}
