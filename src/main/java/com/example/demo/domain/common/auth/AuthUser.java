package com.example.demo.domain.common.auth;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Boolean authenticate(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.getPassword());
    }
}
