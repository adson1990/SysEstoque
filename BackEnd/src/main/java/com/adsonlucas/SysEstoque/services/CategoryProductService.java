
package com.adsonlucas.SysEstoque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.Functions;
import com.adsonlucas.SysEstoque.entities.CategoryProduct;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryProductDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.CategoryProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryProductService {
	
	@Autowired
	private CategoryProductRepository repository;
	private Functions function;
	
	@Transactional(readOnly = true)
	public Page<CategoryProductDTO> findAllPaged(Pageable pageable) {
		Page<CategoryProduct> list = repository.findAll(pageable);
		
		return list.map(x -> new CategoryProductDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryProductDTO findByID(Long ID) {
		Optional<CategoryProduct> opt = repository.findById(ID);
		CategoryProduct categoryEntity = opt.orElseThrow(() -> new EntidadeNotFoundException("Categoria de produto não encontrada no BD."));
		
		return new CategoryProductDTO(categoryEntity);
	}
	
	@Transactional
	public CategoryProductDTO insCatProduct(CategoryProductDTO dto) {
		CategoryProduct product = new CategoryProduct();
		product = function.copyDTOToEntityCategoryProduct(dto, product);
		product	=	repository.save(product);
		
		return new CategoryProductDTO(product);
	}
	
	@Transactional
	public CategoryProductDTO updProduct(CategoryProductDTO dto, Long id) {
		try {
			CategoryProduct product = repository.getReferenceById(id);
			product = function.copyDTOToEntityCategoryProduct(dto, product);
			product = repository.save(product);
		
			return new CategoryProductDTO(product);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Categoria de produto não atualizada, ID não encontrado.");
		}
	}
	
	@Transactional
	public void delProduct(Long ID) {
		try {
			repository.deleteById(ID);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNotFoundException("Categoria de produto não encontrada com o ID." + e.getMessage());
			
		}catch(DataIntegrityViolationException d) {
			throw new DataBaseException("Violação de integridade do DB ao deletar produto");
		}
	}

}
