package com.adsonlucas.SysEstoque;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.CategoryProduct;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.Enderecos;
import com.adsonlucas.SysEstoque.entities.Product;
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryProductDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.EnderecosDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ProductDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.RolesDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;

@SuppressWarnings("unused")
@Component
public class Functions {
		
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

public Client copyDTOToEntityClient(ClientDTO dto, Client entity) {	
	//BeanUtils.copyProperties(dto, entity);
	
	entity.setName(dto.getName());
    entity.setCpf(dto.getCpf());
    entity.setIncome(dto.getIncome());
    entity.setBirthDate(dto.getBirthDate());
    entity.setSexo(dto.getSexo());
    entity.setEmail(dto.getEmail());
    entity.setSenha(dto.getSenha());
    
    return entity;
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
