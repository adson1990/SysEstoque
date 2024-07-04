package com.adsonlucas.SysEstoque.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.Functions;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeExistenteException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@EnableMethodSecurity
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private Functions function;
	
	//CRUD
	
	//Todos clientes
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPages(PageRequest pageRequest){
		Page<Client> pageList = clientRepository.findAll(pageRequest);
		
		return pageList.map(x -> new ClientDTO(x, x.getCategories()));
	}
	
	// Client By ID
	@Transactional(readOnly = true)
	public ClientDTO findById(Long ID) {
		Optional<Client> clientById = clientRepository.findById(ID);
		Client clientEntity = clientById.orElseThrow(() -> new EntidadeNotFoundException("Cliente não encontrado pelo ID informado."));
		
		return new ClientDTO(clientEntity, clientEntity.getCategories());
	}
	
	// Insert Client
	@Transactional
	public ClientDTO insClient(ClientDTO dto) {
		verificaCliente(dto.getCpf());
			Client client = new Client();
			client = function.copyDTOToEntityClient(dto, client);
			client = clientRepository.save(client);
		
		return new ClientDTO(client);
	}
	
	//UPDATES
	//Atualiza cliente
	@Transactional
	public ClientDTO updClient(ClientDTO dto, Long ID) {
		verificaCliente(dto.getCpf());
		try {
			ClientDTO clientDTO = findById(ID);
			Client client = new Client(dto, clientDTO.getID());
			clientRepository.save(client);
		
			return new ClientDTO(client);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Cliente não atualizado, ID não encontrado.");
		}
	}
	
	//Apaga Cliente
	@Transactional
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
	public void delClient(Long ID) {
		Optional<Client> clientOPT = clientRepository.findById(ID);

		if (clientOPT.isPresent()) {
			try {
				clientRepository.deleteById(ID);
			} catch(DataIntegrityViolationException d) {
				throw new DataBaseException("Violação de integridade do DB.");
			}
		}else { 
			throw new EntidadeNotFoundException("Cliente não encontrado com o ID: " + ID);
		
		} 
	}
	
	//Métodos customizados
	
	//Verificar se já existe cliente antes de inserir ou atualizar.
	public void verificaCliente(String cpf) {
		
		if (clientRepository.findByCpf(cpf).isPresent()) {
			throw new EntidadeExistenteException("CPF já cadastrado no Banco de Dados.");
		}
	}

}
