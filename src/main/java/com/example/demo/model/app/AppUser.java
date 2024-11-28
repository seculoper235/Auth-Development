package com.example.demo.model.app;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "\"User\"")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;
}
