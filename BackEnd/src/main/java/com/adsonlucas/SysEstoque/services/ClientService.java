package com.adsonlucas.SysEstoque.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	//CONSULTAS
	//Todos clientes
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPages(PageRequest pageRequest){
		Page<Client> pageList = clientRepository.findAll(pageRequest);
		
		return pageList.map(x -> new ClientDTO(x, x.getCategories()));
	}
	
	// Client By ID
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> clientById = clientRepository.findById(id);
		Client clientEntity = clientById.orElseThrow(() -> new EntidadeNotFoundException("Cliente não encontrado pelo ID informado."));
		
		return new ClientDTO(clientEntity, clientEntity.getCategories());
	}
	
	//INSERTS
	// Insert Client
	@Transactional
	public ClientDTO insClient(ClientDTO dto) {
		Client entity = new Client();
		copyDTOToEntity(dto, entity);
		entity = clientRepository.save(entity);
		
		return new ClientDTO(entity);
	}
	
	//UPDATES
	//Atualiza cliente
	@Transactional
	public ClientDTO updClient(ClientDTO dto, Long id) {
		try {
			Client client = clientRepository.getReferenceById(id);
			copyDTOToEntity(dto, client);
			client = clientRepository.save(client);
		
			return new ClientDTO(client);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Cliente não atualizado, ID não encontrado.");
		}
	}
	
	//DELETES
	//Apaga Cliente
	public void delClient(Long ID) {
		Optional<Client> clientOPT = clientRepository.findById(ID);

		if (clientOPT.isPresent()) {
			try {
				clientRepository.deleteById(ID);
			} catch (DataIntegrityViolationException e) {
				throw new DataBaseException("Violação de integridade do DB");
			}
		}else {
			throw new EntidadeNotFoundException("Cliente não encontrado com o ID: " + ID);
		} 
	}
	
	//Métodos customizados
	private void copyDTOToEntity(ClientDTO dto, Client entity) {
		
		entity = new Client(dto);

	}

}
