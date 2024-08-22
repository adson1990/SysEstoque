package com.adsonlucas.SysEstoque.services;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.Functions;
import com.adsonlucas.SysEstoque.entities.Celphone;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.Enderecos;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.CelphoneDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.EnderecosDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeExistenteException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service
@EnableMethodSecurity
public class ClientService {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private Functions function;
	
	//CRUD
	
	//Todos Endereços
	@Transactional(readOnly = true)
    public void printEnderecos(Long pessoaId) {
        Client pessoa = clientRepository.findPessoaWithEnderecos(pessoaId);
        System.out.println("Nome da pessoa: " + pessoa.getName());

        for (Enderecos endereco : pessoa.getEnderecos()) {
            System.out.println("Endereço: " + endereco.getRua());
        }
    }
	
	//Todos números
		@Transactional(readOnly = true)
	    public void printCelphones(Long pessoaId) {
	        Client pessoa = clientRepository.findPessoaWithCelphone(pessoaId);
	        System.out.println("Nome da pessoa: " + pessoa.getName());

	        for (Celphone celphone : pessoa.getCel()) {
	            System.out.println("Telefone: " + celphone.getDdd() + " " + celphone.getNumber());
	        }
	    }
	
	//Todos clientes
	@Cacheable("clientes")
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPages(PageRequest pageRequest){
		Page<Client> pageList = clientRepository.findAll(pageRequest);
		
		return pageList.map(x -> new ClientDTO(x, x.getCategories(), x.getEnderecos(), x.getCel()));
	}
	
	// Client By ID
	@Transactional(readOnly = true)
	public ClientDTO findById(Long ID) {
		Optional<Client> clientById = clientRepository.findById(ID);
		Client clientEntity = clientById.orElseThrow(() -> new EntidadeNotFoundException("Cliente não encontrado pelo ID informado."));
		
		return new ClientDTO(clientEntity, clientEntity.getCategories(), clientEntity.getEnderecos(), clientEntity.getCel());
	}
	
	// Insert Client
	@Transactional
	//@Async("taskExecutor") // execução desta chamada de forma assíncrona
	public ClientDTO insClient(ClientDTO dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		verificaCliente(dto.getCpf());
		
		var senhaCripto = encoder.encode(dto.getSenha());
		dto.setSenha(senhaCripto);
		
		Client client = new Client();
		client = function.copyDTOToEntityClient(dto, client);
	
		client = clientRepository.save(client);
		
		Set<CategoryClientDTO> categoryDTOs = new HashSet<>();
        categoryDTOs.add(new CategoryClientDTO(4L));
        categoryDTOs.add(new CategoryClientDTO(6L));
        dto.setCategories(categoryDTOs);
        
        Long clientId = client.getID();
        insertClientCategories(clientId, dto.getCategories());
        insertClientEnderecos(dto.getEnderecos(), clientId);
        insertClientCelphones(dto.getCelphone(), clientId);
        
		
		return new ClientDTO(client, client.getCategories(), client.getEnderecos(), client.getCel());
	}
	
	//UPDATES
	//Atualiza cliente
	@Transactional
	public ClientDTO updClient(ClientDTO dto, Long ID) {
		verificaCliente(dto.getCpf());
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			ClientDTO clientDTO = findById(ID);
			var senhaCripto = encoder.encode(dto.getSenha());
			dto.setSenha(senhaCripto);
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
	
	// Salva o cliente com as 2 categorias padrão iniciais
	private void insertClientCategories(Long clientId, Set<CategoryClientDTO> categories) {
        for (CategoryClientDTO category : categories) {
            String sql = "INSERT INTO TB_CLIENT_CATEGORY (client_id, categoryclient_id) VALUES (:clientId, :categoryId)";
            entityManager.createNativeQuery(sql)
                    .setParameter("clientId", clientId)
                    .setParameter("categoryId", category.getID())
                    .executeUpdate();
        }
    }
	
	private void insertClientEnderecos(List<EnderecosDTO> enderecos, Long idClient) {
		for (EnderecosDTO enderecoDTO : enderecos) {
			String sql = "INSERT INTO TB_ENDERECOS (RUA, BAIRRO, NUM, ESTADO, COUNTRY, CEP, CLIENT_ID) VALUES (:rua, :bairro, :num, :estado, :country, :cep, :client_id)";
			entityManager.createNativeQuery(sql)
						.setParameter("rua", enderecoDTO.getRua())
						.setParameter("bairro", enderecoDTO.getBairro())
						.setParameter("estado", enderecoDTO.getEstado())
						.setParameter("num", enderecoDTO.getNum())
						.setParameter("country", enderecoDTO.getCountry())
						.setParameter("cep", enderecoDTO.getCep())
						.setParameter("client_id", idClient)
						.executeUpdate();
		}
	}
	
	private void insertClientCelphones(List<CelphoneDTO> celphoneDTO, Long clientID) {
		for (CelphoneDTO cel : celphoneDTO) {
			String sql = "INSERT INTO TB_CELPHONE (DDD, NUMBER, TIPO, CLIENT_ID) VALUES (:ddd, :number, :tipo, :client_id)";
			entityManager.createNativeQuery(sql)
			.setParameter("ddd", cel.getDdd())
			.setParameter("number", cel.getNumber())
			.setParameter("tipo", cel.getTipo())
			.setParameter("client_id", clientID)
			.executeUpdate();
		}
	}

}
