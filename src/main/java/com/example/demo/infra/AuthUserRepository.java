package com.example.demo.infra;

import com.example.demo.model.common.auth.AuthUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);

    default AuthUser getByEmail(String email) {
        return this.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }
}
