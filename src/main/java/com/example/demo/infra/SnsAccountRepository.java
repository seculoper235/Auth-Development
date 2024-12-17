package com.example.demo.infra;

import com.example.demo.model.common.auth.SnsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SnsAccountRepository extends JpaRepository<SnsAccount, Long> {
    Optional<SnsAccount> findByUid(String uid);

    void deleteByUid(String uid);

    @Transactional
    @Modifying
    @Query("update SnsAccount s set s.refreshToken = ?1 where s.uid = ?2")
    void updateRefreshTokenByUid(String refreshToken, String uid);
}
