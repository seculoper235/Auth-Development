package com.example.demo.model.common.auth;

import com.example.demo.service.auth.SnsAccountInfo;
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

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;

    public SnsAccountInfo toInfo() {
        return new SnsAccountInfo(id, uid, type);
    }
}
