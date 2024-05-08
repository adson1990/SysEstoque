package com.adsonlucas.SysEstoque.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;

@ExtendWith(SpringExtension.class)
public class ClientServiceTests {

	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	private long idExistente;
	private long idNaoExistente;
	private long idDependente;
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 100L;
		idDependente = 4L;
		
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(idDependente);
	}
	
	@Test
	public void deleteFazNadaQuandoIdExistir() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delClient(idExistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
	}
	
	@Test
	public void deleteDeveLancarEntidadeNotFoundQuandoIdNaoExistir() {
		
		Assertions.assertThrows(EntidadeNotFoundException.class, () -> {
			service.delClient(idNaoExistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	@Test
	public void deleteDeveLancarDataBaseExceptionQuandoIdNaoExistir() {
		
		Assertions.assertThrows(DataBaseException.class, () -> {
			service.delClient(idDependente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNaoExistente);
	}
}
