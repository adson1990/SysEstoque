package com.adsonlucas.SysEstoque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.RefreshTokenClient;
import com.adsonlucas.SysEstoque.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenClient, Long> {
    Optional<RefreshTokenClient> findByRefreshToken(String refreshToken);
    void deleteByUser(User user);
    Optional<RefreshTokenClient> findByClient(Client client);
    Optional<RefreshTokenClient> findByUser(User user);

}
