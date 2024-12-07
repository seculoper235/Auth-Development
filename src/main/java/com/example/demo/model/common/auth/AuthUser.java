package com.example.demo.model.common.auth;

import com.example.demo.service.auth.AuthUserInfo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Getter
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

    @OneToMany(mappedBy = "authUser")
    private List<SnsAccount> snsAccounts = Collections.emptyList();

    public Boolean matchPassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.getPassword());
    }

    public AuthUserInfo toInfo() {
        return new  AuthUserInfo(id, name, email, snsAccounts.stream().map(SnsAccount::toInfo).toList());
    }
}
