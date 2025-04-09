package com.adsonlucas.SysEstoque.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class RefreshTokenClient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    private User user;
    
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = true)
    private Client client;
    
    // constructors
    public RefreshTokenClient() {
    	
    }
    
	public RefreshTokenClient(String refreshToken, Instant expiryDate, Client client) {
		super();
		this.refreshToken = refreshToken;
		this.expiryDate = expiryDate;
		this.client = client;
	}	
	
    
    public RefreshTokenClient(String refreshToken, Instant expiryDate, User user) {
		super();
		this.refreshToken = refreshToken;
		this.expiryDate = expiryDate;
		this.user = user;
	}


	// getters and setters
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Instant getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

	public Long getId() {
		return id;
	}    
    
}
