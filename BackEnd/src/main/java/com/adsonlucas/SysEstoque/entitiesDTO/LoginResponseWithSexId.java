package com.adsonlucas.SysEstoque.entitiesDTO;

public record LoginResponseWithSexId(String accessToken, Long expiresIn, String refreshToken, Character sexo, Long id, String foto) {
}
