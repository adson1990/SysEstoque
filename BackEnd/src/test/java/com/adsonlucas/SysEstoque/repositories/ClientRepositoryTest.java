package com.adsonlucas.SysEstoque.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.adsonlucas.SysEstoque.entities.Client;

@DataJpaTest
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository repoTest;
	
	@Test
	public void deleteObjetoQuandoIdExistir() {
		repoTest.deleteById(1L);
		
		Optional<Client> result = repoTest.findById(1L);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteResultaErroQuandoIdNaoExistir() {
		
		long IdNaoExistente = 1000L;
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repoTest.deleteById(IdNaoExistente);
		});
	}
}
