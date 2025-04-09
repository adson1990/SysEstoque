package com.adsonlucas.SysEstoque;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.CategoryProduct;
import com.adsonlucas.SysEstoque.entities.Cellphone;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.Enderecos;
import com.adsonlucas.SysEstoque.entities.Product;
import com.adsonlucas.SysEstoque.entities.RefreshTokenClient;
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryProductDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.CellphoneDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.EnderecosDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ProductDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.RolesDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;
import com.adsonlucas.SysEstoque.repositories.RefreshTokenRepository;
import com.adsonlucas.SysEstoque.services.ClientService;

@SuppressWarnings("unused")
@Component
public class Functions {
	
	private RefreshTokenRepository refreshRepository;
	private CategoryClient entityCategoryClient;
	private CategoryProduct entityCategoryProduct;
	private Client client;
	private Product product;
	private Roles role;
	private User user;
	
public CategoryClient copyDTOToEntityCategoryClient(CategoryClientDTO dto, CategoryClient entity) {
		
	return	entityCategoryClient = new CategoryClient(dto);
	}

public CategoryProduct copyDTOToEntityCategoryProduct(CategoryProductDTO dto, CategoryProduct entity) {
	
	return entityCategoryProduct = new CategoryProduct(dto);
	}

public Client copyAllDataDTOToEntity(ClientDTO dto, Client entity) {
	BeanUtils.copyProperties(dto, entity);
	
	return entity;
}

public Client copyDTOToEntityClient(ClientDTO dto, Client entity) {
	//BeanUtils.copyProperties(dto, entity);
	
	entity.setName(dto.getName());
    entity.setIncome(dto.getIncome());
    entity.setBirthDate(dto.getBirthDate());
    entity.setEmail(dto.getEmail());
    entity.setFoto(dto.getFoto());
		
	// usando o .clear() gera uma nova linha mudando o ID no DATABASE
	if (dto.getEnderecos() != null && !dto.getEnderecos().isEmpty()) {
		entity.getEnderecos().clear();
	  
		List<Enderecos> enderecos =
				dto.getEnderecos().stream().map(this::convertEnderecoDTOToEntity).toList();
		enderecos.forEach(e -> e.setClient(entity));
		entity.getEnderecos().addAll(enderecos); }
    
		// usando o método updateCellphones tem uma implementação onde não modifica o ID no DB
    if (dto.getCellphones() != null && !dto.getCellphones().isEmpty()) { 
    	//entity.getCel().clear(); ao apagar para inserir causa erro de unique no number	
		 
    	updateCellphones(entity, dto.getCellphones());
	}
	
    return entity;
	}

private Enderecos convertEnderecoDTOToEntity(EnderecosDTO dto) {
    Enderecos endereco = new Enderecos();
    endereco.setRua(dto.getRua());
    endereco.setBairro(dto.getBairro());
    endereco.setNum(dto.getNum());
    endereco.setCidade(dto.getCidade());
    endereco.setEstado(dto.getEstado());
    endereco.setCountry(dto.getCountry());
    endereco.setCep(dto.getCep());
    return endereco;
}

private Cellphone convertCellphoneDTOToEntity(CellphoneDTO dto) {
    Cellphone cellphone = new Cellphone();
    cellphone.setDdd(dto.getDdd());
    cellphone.setNumber(dto.getNumber());
    cellphone.setTipo(dto.getTipo());
    return cellphone;
}

private void updateCellphones(Client entity, List<CellphoneDTO> newPhonesDTO) {
    // Mapeia os telefones existentes por número
    Map<String, Cellphone> existingPhones = entity.getCel().stream()
        .collect(Collectors.toMap(Cellphone::getNumber, Function.identity()));

    List<Cellphone> updatedPhones = new ArrayList<>();

    for (CellphoneDTO phoneDTO : newPhonesDTO) {
        Cellphone existingPhone = existingPhones.get(phoneDTO.getNumber());

        if (existingPhone != null) {
            // Atualiza apenas os campos necessários se o telefone já existir
            existingPhone.setDdd(phoneDTO.getDdd());
            existingPhone.setTipo(phoneDTO.getTipo());
            updatedPhones.add(existingPhone);
        } else {
            // Adiciona novos telefones
            Cellphone newPhone = convertCellphoneDTOToEntity(phoneDTO);
            newPhone.setClient(entity);
            updatedPhones.add(newPhone);
        }
    }

    // Define a nova lista de celulares
    entity.getCel().clear();
    entity.getCel().addAll(updatedPhones);
}

public Product copyDTOToEntityProduct(ProductDTO dto, Product entity) {	
	return entity = new Product(dto);
	}

public Roles copyDTOToEntityRole(RolesDTO dto, Roles entity) {		
	BeanUtils.copyProperties(dto, entity);
	
	return entity;
	}

public User copyDTOToEntityUser(UserDTO dto, User entity) {			
	 BeanUtils.copyProperties(dto, entity);
	 
	 return entity;
	}

}
