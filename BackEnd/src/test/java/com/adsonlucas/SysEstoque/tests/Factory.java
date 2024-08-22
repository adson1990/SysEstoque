package com.adsonlucas.SysEstoque.tests;

import java.time.Instant;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;

public class Factory {
	
	public static Client createdClient() {
		Client cliente = new Client("JOSE SARAMAGO", "674.028.960-30", 1850.0, Instant.parse("1997-10-10T20:50:07.12345Z"), 'M',"SARAMAGO@EXEMPLO.COM", "$2a$10$qGMvT3AvtGNJeZEhCEkkSuHQ3iXRkLlIxcdifuP918K0aXK243Bla");
		return cliente;
	}
	
	public static ClientDTO createdClientDTO2() {
		ClientDTO cliente = new ClientDTO("ABGAIL ABIGOBAL", "892.359.520-93", 2035.5, Instant.parse("1994-08-17T20:50:07.12345Z"), "ABGAIL@EXEMPLO.COM", 'F', "$2a$10$qGMvT3AvtGNJeZEhCEkkSuHQ3iXRkLlIxcdifuP918K0aXK243Bla");		
				
		return cliente;
	}
	
	public static ClientDTO createClientDTO() {
		Client cliente = new Client();
		return new ClientDTO(cliente);
	}
	
	public static CategoryClient createCategory() {
		CategoryClient category = new CategoryClient("novo cliente");
		
		return category;
	}

}
