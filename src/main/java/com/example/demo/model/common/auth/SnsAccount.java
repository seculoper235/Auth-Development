package com.example.demo.model.common.auth;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "\"SNS_ACCOUNT\"")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;
}
