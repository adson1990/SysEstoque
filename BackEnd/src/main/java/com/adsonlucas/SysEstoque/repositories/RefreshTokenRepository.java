package com.adsonlucas.SysEstoque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.RefreshTokenClient;
import com.adsonlucas.SysEstoque.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenClient, Long> {
    Optional<RefreshTokenClient> findByRefreshToken(String refreshToken);
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshTokenClient r WHERE r.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
    Optional<RefreshTokenClient> findByClient(Client client);
    Optional<RefreshTokenClient> findByUser(User user);

}
