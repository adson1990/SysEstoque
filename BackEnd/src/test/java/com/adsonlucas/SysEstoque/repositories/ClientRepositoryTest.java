package com.adsonlucas.SysEstoque.repositories;

//import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.services.ClientService;
import com.adsonlucas.SysEstoque.tests.Factory;
//import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;

@DataJpaTest
@ActiveProfiles("test")
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository repoTest;
	
	//private ClientService service;
	
	private long idNaoExistente;
	private long idExistente;
	private long countTotalClientes;
	
	@BeforeEach
	void setUp() throws Exception{
		idNaoExistente = 100L;
		idExistente = 1L;
		countTotalClientes = 10L;
	}
	
	@Test
	public void deleteObjetoQuandoIdExistir() {
		
		
		repoTest.deleteById(idExistente);
		
		Optional<Client> result = repoTest.findById(1L);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void saveDeveGravarComAutoIncrementQuandoIdNulo() {
		
		Client cliente = Factory.createdClient();
		
		cliente = repoTest.save(cliente);
		
		Assertions.assertNotNull(cliente.getID());
		Assertions.assertEquals(countTotalClientes + 1, cliente.getID());
	}
	
 /*	@Test
	public void deleteResultaErroQuandoIdNaoExistir() {
		
		long idNaoExistente = 100L;
		
		/*assertThrows(EntidadeNotFoundException.class, () -> {
			service.delClient(idNaoExistente);
		}); 
		
		 Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repoTest.deleteById(idNaoExistente);
		}); 
	} */
}
