package com.example.demo.infra;

import com.example.demo.model.common.auth.SnsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsAccountRepository extends JpaRepository<SnsAccount, Long> {
    Optional<SnsAccount> findByUid(String uid);

    void deleteByUid(String uid);
}
