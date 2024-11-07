package com.adsonlucas.SysEstoque.entitiesDTO;

public record TokenRefreshResponse(String accessToken, Long expiresIn, String refreshToken) {

}
