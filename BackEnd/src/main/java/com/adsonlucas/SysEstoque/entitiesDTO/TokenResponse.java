package com.adsonlucas.SysEstoque.entitiesDTO;

public record TokenResponse(String accessToken, Long expiresInSeconds, String refreshToken) {

}
