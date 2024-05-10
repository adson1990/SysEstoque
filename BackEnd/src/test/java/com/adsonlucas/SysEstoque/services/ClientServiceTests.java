package com.adsonlucas.SysEstoque.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;
import com.adsonlucas.SysEstoque.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ClientServiceTests {

	@InjectMocks
	private ClientService service;
	
	@Mock
	private ClientRepository repository;
	
	private long idExistente;
	private long idNaoExistente;
	private long idDependente;
	private PageImpl<Client> page;
	private Client cliente;
	private ClientDTO clienteDTO;
	private Client cliente2;
	List<CategoryClient> list = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 20L;
		idDependente = 2L;
		cliente = Factory.createdClient();	
		clienteDTO = Factory.createClientDTO();
		
		
		list.add(new CategoryClient("nova categoria 1"));
		list.add(new CategoryClient("nova categoria 2"));
		Set<CategoryClient> targetSet = new HashSet<>(list);
		
		cliente2 = new Client(clienteDTO, targetSet);				
		
		repository.save(cliente);
		repository.save(cliente2);
		
		page = new PageImpl<>(List.of(cliente));
		
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(cliente);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(cliente2);	
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(cliente));
		Mockito.when(repository.findById(idDependente)).thenReturn(Optional.of(cliente2));
		Mockito.when(repository.findById(idNaoExistente)).thenReturn(Optional.empty());	
		//Mockito.when(repository.findAll()).thenReturn(page);	
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doNothing().when(repository).deleteById(idNaoExistente);
		//Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
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
		
		Mockito.verify(repository, Mockito.times(0)).deleteById(idNaoExistente);
	} 
	
	@Test
	public void deleteDeveLancarDataBaseExceptionQuandoObjetoForDependente() {		
	
		Assertions.assertThrows(DataBaseException.class, () -> {
			service.delClient(idDependente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idDependente);
	}  
}
