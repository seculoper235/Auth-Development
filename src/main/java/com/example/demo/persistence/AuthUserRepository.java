package com.example.demo.persistence;

import com.example.demo.domain.common.auth.AuthUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByEmail(String email);

    default AuthUser getByEmail(String email) throws EntityNotFoundException {
        return findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }
}
