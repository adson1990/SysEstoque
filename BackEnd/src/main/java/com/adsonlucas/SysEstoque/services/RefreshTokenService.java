package com.adsonlucas.SysEstoque.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.RefreshTokenClient;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;
import com.adsonlucas.SysEstoque.repositories.RefreshTokenRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;
import com.adsonlucas.SysEstoque.resouces.exceptions.TokenRefreshException;

@Service
public class RefreshTokenService {
	
	private static Logger loggerClient = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    public RefreshTokenClient createRefreshToken(Long userId) {
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    	
    	Optional<RefreshTokenClient> existingTokenOpt = refreshTokenRepository.findByUser(user);
    	
        RefreshTokenClient refreshTokenClient;
        
        if(existingTokenOpt.isPresent()) {
        	refreshTokenClient = existingTokenOpt.get();
        	verifyExpiration(refreshTokenClient);
        	
        	refreshTokenClient.setExpiryDate(Instant.now().plusMillis(600)); 
            refreshTokenClient.setRefreshToken(UUID.randomUUID().toString()); 
        } else {
        	refreshTokenClient = new RefreshTokenClient();	
        	refreshTokenClient.setUser(userRepository.findById(userId).get());
        	refreshTokenClient.setExpiryDate(Instant.now().plusMillis(600)); // 10 min
        	refreshTokenClient.setRefreshToken(UUID.randomUUID().toString());
        }

        refreshTokenClient = refreshTokenRepository.save(refreshTokenClient);
        return refreshTokenClient;
    }
    
    public RefreshTokenClient createClientRefreshToken(Long clientId) {
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));

        // Já existe token para o cliente?
        Optional<RefreshTokenClient> existingTokenOpt = refreshTokenRepository.findByClient(client);

        RefreshTokenClient refreshTokenClient;

        if (existingTokenOpt.isPresent()) {
            // Atualiza o token existente
        	loggerClient.info("RefreshToken já existente.");
            refreshTokenClient = existingTokenOpt.get();
            verifyExpiration(refreshTokenClient);
            refreshTokenClient.setExpiryDate(Instant.now().plusMillis(600)); // Renova a expiração
            refreshTokenClient.setRefreshToken(UUID.randomUUID().toString()); // Gera um novo token se necessário
        } else {
            // Cria um novo token
        	loggerClient.info("Novo RefreshToken");
            refreshTokenClient = new RefreshTokenClient();
            refreshTokenClient.setClient(client);
            refreshTokenClient.setRefreshToken(UUID.randomUUID().toString());
            refreshTokenClient.setExpiryDate(Instant.now().plusMillis(600)); // Define a expiração
        }

        return refreshTokenRepository.save(refreshTokenClient);
    }


    public RefreshTokenClient verifyExpiration(RefreshTokenClient token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getRefreshToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }
    
    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
    
    @Transactional(readOnly = true)
    public Optional<RefreshTokenClient> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
}
