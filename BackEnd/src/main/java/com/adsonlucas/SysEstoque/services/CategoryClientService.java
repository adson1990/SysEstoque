
package com.adsonlucas.SysEstoque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.CategoryClient;
import com.adsonlucas.SysEstoque.entitiesDTO.CategoryClientDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.CategoryClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryClientService {
	
	@Autowired
	private CategoryClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryClientDTO> findAllPaged(Pageable pageable) {
		Page<CategoryClient> list = repository.findAll(pageable);
		
		return list.map(x -> new CategoryClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryClientDTO findByID(Long ID) {
		Optional<CategoryClient> opt = repository.findById(ID);
		CategoryClient categoryEntity = opt.orElseThrow(() -> new EntidadeNotFoundException("Categoria de cliente não encontrada no BD."));
		
		return new CategoryClientDTO(categoryEntity);
	}
	
	@Transactional
	public CategoryClientDTO insCatClient(CategoryClientDTO dto) {
		CategoryClient client = new CategoryClient();
		copyDTOToEntity(dto, client);
		client	=	repository.save(client);
		
		return new CategoryClientDTO(client);
	}
	
	@Transactional
	public CategoryClientDTO updClient(CategoryClientDTO dto, Long id) {
		try {
			CategoryClient client = repository.getReferenceById(id);
			copyDTOToEntity(dto, client);
			client = repository.save(client);
		
			return new CategoryClientDTO(client);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Categoria de cliente não atualizada, ID não encontrado.");
		}
	}
	
	@Transactional
	public void delClient(Long ID) {
		try {
			repository.deleteById(ID);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNotFoundException("Categoria de cliente não encontrada com o ID." + e.getMessage());
			
		}catch(DataIntegrityViolationException d) {
			throw new DataBaseException("Violação de integridade do DB ao deletar cliente.");
		}
	}

	
	private void copyDTOToEntity(CategoryClientDTO dto, CategoryClient entity) {
		
		entity = new CategoryClient(dto);

	}
}
