package com.adsonlucas.SysEstoque.services;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adsonlucas.SysEstoque.Functions;
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.UserDTO;
import com.adsonlucas.SysEstoque.exceptions.DataBaseException;
import com.adsonlucas.SysEstoque.exceptions.EntidadeNotFoundException;
import com.adsonlucas.SysEstoque.repositories.RolesRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@EnableMethodSecurity
public class UserService implements UserDetailsService{

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RolesRepository roleRepository;
	private BCryptPasswordEncoder passwordEnconder;
	LoginRequest loginRequest;
	
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
		passwordEnconder = new BCryptPasswordEncoder();
		User user = new User();
		var basicRole = roleRepository.findByAuthority(Roles.Values.BASIC.name());
		user = function.copyDTOToEntityUser(dto, user);
		user.setSenha(passwordEnconder.encode(dto.getSenha())); //encriptar a senha
		user.setRoles(Set.of(basicRole)); //adicionar regra basica, padrão na criação de usuários
		user = userRepository.save(user);
		
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO updUser(Long ID, UserDTO dto) {
		try {		
		UserDTO userDTO = findById(ID);
		User user = new User(dto, userDTO.getID());
		
		userRepository.save(user);
		
		return new UserDTO(user);
		}catch(EntityNotFoundException e) {
			throw new EntidadeNotFoundException("Usuário não encontrado.");
		}
	}
	
	@Transactional
	@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		Optional<User> userOptional = userRepository.findByNome(username);
		passwordEnconder = new BCryptPasswordEncoder();
		
		if (userOptional.isEmpty()) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}else if (userOptional.get().isEnabled()) {
			logger.warn("Conta Bloqueada. " + username); 
			throw new LockedException("Conta bloqueada, favor entrar em contato com o suporte.");
		}
		
		logger.info("User found: " + username);
		User user = userOptional.get();
		return user;
	} 
	
}