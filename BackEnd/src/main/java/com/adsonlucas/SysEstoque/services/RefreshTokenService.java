package com.adsonlucas.SysEstoque.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class RefreshTokenService {
	
	private static Logger loggerClient = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @PersistenceContext
    private EntityManager entityManager;


    public RefreshTokenClient createRefreshToken(Long userId) {
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    	
    	Optional<RefreshTokenClient> existingTokenOpt = refreshTokenRepository.findByUser(user);
    	
        RefreshTokenClient refreshTokenClient;
        
        if(existingTokenOpt.isPresent()) {
        	refreshTokenClient = existingTokenOpt.get();
        	verifyExpiration(refreshTokenClient);
        	
        	refreshTokenClient.setExpiryDate(Instant.now().plusMillis(600_000)); 
            refreshTokenClient.setRefreshToken(UUID.randomUUID().toString()); 
        } else {
        	refreshTokenClient = new RefreshTokenClient();	
        	refreshTokenClient.setUser(userRepository.findById(userId).get());
        	refreshTokenClient.setExpiryDate(Instant.now().plusMillis(600_000)); // 10 min
        	refreshTokenClient.setRefreshToken(UUID.randomUUID().toString());
        }

        refreshTokenClient = refreshTokenRepository.save(refreshTokenClient);
        return refreshTokenClient;
    }
    
    /* Adson Farias -> 16/04/2025 a cada novo login o refreshtoken será renovado 
     * Deletando o antigo e criando um novo*/
    @Transactional
    public Boolean deleteClientRefreshToken(Long clientId) {
    	AtomicBoolean resposta = new AtomicBoolean(false);
    	
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new IllegalArgumentException("Client not found: " + clientId));
        
     // trecho provisório para investigação.
     	/*	java.util.List<RefreshTokenClient> allTokens = refreshTokenRepository.findAll();
     		loggerClient.info("Total de tokens encontrados: " + allTokens.size());
     		allTokens.forEach(token ->
     		    loggerClient.info("Token para client ID: " + token.getClient().getID())
     		); */
        
        // Verifica e deleta o token existente, se houver
        refreshTokenRepository.findByClient(client)
            .ifPresent(existingToken -> {
                loggerClient.info("Deletando RefreshToken existente");
                refreshTokenRepository.deleteByUserId(client.getID());
                entityManager.flush();
                resposta.set(true);
            }); 
        return resposta.get();	
    }
    
    /*Adson Farias  16/04/2025 -> Endpoint para renovar o time do refresh token
     * desde que não tenha expirado ainda e não tenha feito logout.*/
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
            refreshTokenClient.setExpiryDate(Instant.now().plus(Duration.ofMinutes(10))); // Renova a expiração
            //refreshTokenClient.setExpiryDate(Instant.now().plus(Duration.ofMinutes(1))); // 1 min para teste 
            refreshTokenClient.setRefreshToken(UUID.randomUUID().toString()); // Gera um novo token se necessário
        } else {
            // Cria um novo token
        	loggerClient.info("Novo RefreshToken");
            refreshTokenClient = new RefreshTokenClient();
            refreshTokenClient.setClient(client);
            refreshTokenClient.setRefreshToken(UUID.randomUUID().toString());
            refreshTokenClient.setExpiryDate(Instant.now().plus(Duration.ofMinutes(10))); // Define a expiração
            //refreshTokenClient.setExpiryDate(Instant.now().plus(Duration.ofMinutes(1)));
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
    
    @Transactional(readOnly = true)
    public Optional<RefreshTokenClient> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
}
