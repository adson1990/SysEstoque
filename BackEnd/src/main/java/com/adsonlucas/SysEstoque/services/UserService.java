package com.adsonlucas.SysEstoque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	//BUSCA
	@Transactional(readOnly =true)
	public Page<UserDTO> findAllPages(PageRequest pageRequest){
		Page<User> userList = userRepository.findAll(pageRequest);
		
		return userList.map(x -> new UserDTO(x));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		User userEntity = optional.orElseThrow(() -> new EntidadeNotFoundException("Usuário não encontrado."));
		
		return new UserDTO(userEntity);
	}
	
	@Transactional
	public UserDTO instUser(UserDTO dto) {
		User user = new User();
		copyDTOToEntity(dto, user);
		user = userRepository.save(user);
		
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO updUser(Long ID, UserDTO dto) {
		try {
		User user = userRepository.getReferenceById(ID);
		copyDTOToEntity(dto, user);
		user = userRepository.save(user);
		
		return new UserDTO(user);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Usuário não encontrado.");
		}
	}
	
	@Transactional
	public void delUser(Long ID) {
		try {
			userRepository.deleteById(ID);
		}catch (EmptyResultDataAccessException e1) {
			throw new EntidadeNotFoundException("Usuário não encontrado com o ID, impossível deletar." + e1.getMessage());
		}catch (DataIntegrityViolationException e2) {
			throw new DataBaseException("Violação de banco de dados encontrada." + e2.getMessage());
		}
	}
	
	private void copyDTOToEntity(UserDTO dto, User entity) {		
		entity = new User(dto);
	}
	
}
