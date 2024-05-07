package com.adsonlucas.SysEstoque.tests;

import java.time.Instant;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;

public class Factory {
	
	public static Client createdClient() {
		Client cliente = new Client("JOSE SARAMAGO", "674.028.960-30", 1850.0, Instant.parse("1997-10-10T20:50:07.12345Z"), 1);
		return cliente;
	}
	
	public static ClientDTO createClientDTO() {
		Client cliente = new Client();
		return new ClientDTO(cliente);
	}

}
