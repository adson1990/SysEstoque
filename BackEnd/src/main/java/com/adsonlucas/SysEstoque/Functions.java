package com.adsonlucas.SysEstoque;

import org.springframework.stereotype.Component;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entities.CategoryProduct;
import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.Product;
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryProductDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ClientDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.ProductDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.RolesDTO;
import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;

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
	
	return client = new Client(dto);
	}

public Product copyDTOToEntityProduct(ProductDTO dto, Product entity) {
	
	return product = new Product(dto);
	}

public Roles copyDTOToEntityRole(RolesDTO dto, Roles entity) {		
	return entity = new Roles(dto);
	}

public User copyDTOToEntityUser(UserDTO dto, User entity) {		
	return entity = new User(dto);
	}

}
