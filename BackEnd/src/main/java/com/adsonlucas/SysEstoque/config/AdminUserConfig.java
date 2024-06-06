package com.adsonlucas.SysEstoque.config;

import java.util.Set;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.repositories.RolesRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	

	public AdminUserConfig(RolesRepository rolesRepository, UserRepository userRepository,
						   BCryptPasswordEncoder passwordEncoder) {
		super();
		this.rolesRepository = rolesRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		var roleAdmin = rolesRepository.findByAuthority(Roles.Values.ADMIN.name());
		
		var userAdmin = userRepository.findByNome("admin");
		
		userAdmin.ifPresentOrElse(
								 (user) -> {System.out.println("Admin já existe"); },
								 () -> {
									 var user = new User();
									 user.setNome("admin");
									 user.setSobrenome("");
									 user.setEmail("");
									 user.setIdade(00);
									 user.setFoto("");
									 user.setDt_nascimento(null);
									 user.setSenha(passwordEncoder.encode("123456"));
									 user.setRoles(Set.of(roleAdmin));
									 userRepository.save(user);
								 }
								 );
	}

}
