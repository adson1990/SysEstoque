package com.adsonlucas.SysEstoque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.Functions;
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entitiesDTO.RolesDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.RolesRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RolesService {

	@Autowired
	private RolesRepository roleRepository;
	private Functions function;
	
	//BUSCA
	@Transactional(readOnly =true)
	public Page<RolesDTO> findAllPages(Pageable pageable){
		Page<Roles> roleList = roleRepository.findAll(pageable);
		
		return roleList.map(x -> new RolesDTO(x));
	}
	
	@Transactional(readOnly = true)
	public RolesDTO findById(Long id) {
		Optional<Roles> optional = roleRepository.findById(id);
		Roles roleEntity = optional.orElseThrow(() -> new EntidadeNotFoundException("Usuário não encontrado."));
		
		return new RolesDTO(roleEntity);
	}
	
	@Transactional
	public RolesDTO instRoles(RolesDTO dto) {
		Roles role = new Roles();
		role = function.copyDTOToEntityRole(dto, role);
		role = roleRepository.save(role);
		
		return new RolesDTO(role);
	}
	
	@Transactional
	public RolesDTO updRoles(Long ID, RolesDTO dto) {
		try {
		Roles role = roleRepository.getReferenceById(ID);
		role = function.copyDTOToEntityRole(dto, role);
		role = roleRepository.save(role);
		
		return new RolesDTO(role);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Usuário não encontrado.");
		}
	}
	
	@Transactional
	public void delRoles(Long ID) {
		Optional<Roles> optional = roleRepository.findById(ID);

		if (optional.isPresent()) {
			try {
				//this.findById(ID);
				roleRepository.deleteById(ID);
			} catch(DataIntegrityViolationException d) {
				throw new DataBaseException("Violação de integridade do DB.");
			}
		}else { 
			throw new EntidadeNotFoundException("Usuário não encontrado com o ID: " + ID);
		
		} 
	}

}
