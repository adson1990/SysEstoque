package com.adsonlucas.SysEstoque.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.RefreshToken;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;
import com.adsonlucas.SysEstoque.repositories.RefreshTokenRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;
import com.adsonlucas.SysEstoque.resouces.exceptions.TokenRefreshException;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    public RefreshToken createRefreshToken(Long userId) {
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    	
    	Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByUser(user);
    	
        RefreshToken refreshToken;
        
        if(existingTokenOpt.isPresent()) {
        	refreshToken = existingTokenOpt.get();
        	verifyExpiration(refreshToken);
        	
        	refreshToken.setExpiryDate(Instant.now().plusMillis(36000000)); 
            refreshToken.setToken(UUID.randomUUID().toString()); 
        } else {
        	refreshToken = new RefreshToken();	
        	refreshToken.setUser(userRepository.findById(userId).get());
        	refreshToken.setExpiryDate(Instant.now().plusMillis(36000000)); // 10 horas
        	refreshToken.setToken(UUID.randomUUID().toString());
        }

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    
    public RefreshToken createClientRefreshToken(Long clientId) {
    	// Verifica se o cliente existe
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));

        // Já existe token para o cliente?
        Optional<RefreshToken> existingTokenOpt = refreshTokenRepository.findByClient(client);

        RefreshToken refreshToken;

        if (existingTokenOpt.isPresent()) {
            // Atualiza o token existente
            refreshToken = existingTokenOpt.get();
            verifyExpiration(refreshToken);
            refreshToken.setExpiryDate(Instant.now().plusMillis(36000000)); // Renova a expiração
            refreshToken.setToken(UUID.randomUUID().toString()); // Gera um novo token se necessário
        } else {
            // Cria um novo token
            refreshToken = new RefreshToken();
            refreshToken.setClient(client);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(36000000)); // Define a expiração
        }

        return refreshTokenRepository.save(refreshToken);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }
    
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
    
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
