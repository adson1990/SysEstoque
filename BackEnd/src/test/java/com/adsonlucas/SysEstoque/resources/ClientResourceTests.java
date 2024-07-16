package com.adsonlucas.SysEstoque.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;
import com.adsonlucas.SysEstoque.services.ClientService;
import com.adsonlucas.SysEstoque.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Disabled;

@WithMockUser
@WebMvcTest(ClientResource.class)
public class ClientResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ClientService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Mock
	private ClientRepository repository;
	
	private ClientDTO clientDTO;
	private PageImpl<ClientDTO> page;
	private Long idExistente;
	private Long idNaoExistente;
	private ClientDTO cliente1;
	private ClientDTO cliente2;
	private Client cliente;
	
	@BeforeEach
	void setUp() throws Exception{
		idExistente = 1L;
		idNaoExistente = 20L;
		
		cliente1 = Factory.createClientDTO();
		cliente2 = Factory.createdClientDTO2();
		cliente = Factory.createdClient();

		
		repository.save(cliente);
	
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(cliente);
		when(service.findAllPages(any())).thenReturn(page);
		when(service.findById(idExistente)).thenReturn(clientDTO);
		when(service.findById(idNaoExistente)).thenThrow(EntidadeNotFoundException.class);
		when(service.updClient(any(), eq(idExistente))).thenReturn(clientDTO);
		when(service.updClient(any(), eq(idNaoExistente))).thenThrow(EntidadeNotFoundException.class);
	}
	
	@Test
	public void findAllDeveRetornarPage() throws Exception {
		
		mockMvc.perform(get("/clients")).andExpect(status().isOk());
		}
	
	@Test
	public void findByIdDeveRetornarClienteQuandoIdExistir() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/clients/{ID}", idExistente)
				.accept(MediaType.APPLICATION_JSON));
								
		
		result.andExpect(status().isOk());
		//result.andExpect(jsonPath("$.ID").exists());
	}
	
	@Test
	public void findByIdDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception{
		ResultActions result = mockMvc.perform(get("/clients/{ID}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON));
								
		
		result.andExpect(status().isNotFound());
	}
	
	@Disabled
	@Test
	public void updateDeveRetornarClienteDTOQuandoIdExistir() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(clientDTO);
		
		ResultActions result = mockMvc.perform(put("/clients/{ID}", idExistente)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		//result.andExpect(jsonPath("$.ID").exists());
	}
	
	@Disabled
	@Test
	public void updateDeveRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(clientDTO);
		
		ResultActions result = mockMvc.perform(put("/clients/{ID}", idNaoExistente)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
}
