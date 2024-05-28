package com.adsonlucas.SysEstoque.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.adsonlucas.SysEstoque.Functions;
import com.adsonlucas.SysEstoque.entities.Product;
import com.adsonlucas.SysEstoque.entitiesDTO.ProductDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServices {
	
	@Autowired
	private ProductRepository productRepository;
	//private Functions function;
	
	//Todos produtos
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPages(PageRequest pageRequest){
		Page<Product> pageList = productRepository.findAll(pageRequest);
		
		return pageList.map(x -> new ProductDTO(x, x.getCategories()));
	}
	
	// Product By ID
	@Transactional(readOnly = true)
	public ProductDTO findById(Long ID) {
		Optional<Product> produtoById = productRepository.findById(ID);
		Product productEntity = produtoById.orElseThrow(() -> new EntidadeNotFoundException("Produto não encontrado pelo ID informado."));
		
		return new ProductDTO(productEntity, productEntity.getCategories());
	}
	
	// Insert Product
	@Transactional
	public ProductDTO insProduct(ProductDTO dto) {
		Product product = new Product(dto);
		//product = function.copyDTOToEntityProduct(dto, product);
		product = productRepository.save(product);
		
		return new ProductDTO(product);
	}
	
	//Atualiza produto
	@Transactional
	public ProductDTO updProduct(ProductDTO dto, Long ID) {
		try {
			ProductDTO produtoDTO = findById(ID);
			Product produto = new Product(dto, produtoDTO.getID());
			productRepository.save(produto);
		
			return new ProductDTO(produto);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Produto não atualizado, ID não encontrado.");
		}
	}
	
	//Apaga Producte
	public void delProduct(Long ID) {
		Optional<Product> productOPT = productRepository.findById(ID);

		if (productOPT.isPresent()) {
			try {
				//this.findById(ID);
				productRepository.deleteById(ID);
			} catch(DataIntegrityViolationException d) {
				throw new DataBaseException("Violação de integridade do DB ao excluir produto com vínculo.");
			}
		}else { 
			throw new EntidadeNotFoundException("Produto não encontrado com o ID: " + ID);
		
		} 
	}

}
