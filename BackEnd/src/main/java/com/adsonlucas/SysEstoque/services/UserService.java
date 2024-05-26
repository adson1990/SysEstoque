package com.adsonlucas.SysEstoque.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.Functions;
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
	@Autowired
	private Functions function;
	
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
		user = function.copyDTOToEntityUser(dto, user);
		user = userRepository.save(user);
		
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO updUser(Long ID, UserDTO dto) {
		try {		
		User user = new User(findById(ID));
		user = new User(dto, ID);
		
		user = userRepository.save(user);
		
		return new UserDTO(user);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Usuário não encontrado.");
		}
	}
	
	@Transactional
	public void delUser(Long ID) {
		Optional<User> userOPT = userRepository.findById(ID);

		if (userOPT.isPresent()) {
			try {
				userRepository.deleteById(ID);
			} catch(DataIntegrityViolationException d) {
				throw new DataBaseException("Violação de integridade do DB.");
			}
		}else { 
			throw new EntidadeNotFoundException("Usuário não encontrado com o ID: " + ID);
		
		} 
	}	
}
