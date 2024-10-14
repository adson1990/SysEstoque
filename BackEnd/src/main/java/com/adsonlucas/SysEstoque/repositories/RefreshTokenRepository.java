package com.adsonlucas.SysEstoque.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.RefreshToken;
import com.adsonlucas.SysEstoque.entities.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    Optional<RefreshToken> findByClient(Client client);
    Optional<RefreshToken> findByUser(User user);

}
