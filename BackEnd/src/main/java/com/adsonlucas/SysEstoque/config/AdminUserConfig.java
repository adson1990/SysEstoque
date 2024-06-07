package com.adsonlucas.SysEstoque.config;

import java.time.LocalDate;
import java.util.Set;

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
		
		var roleAdminOptional = rolesRepository.findByAuthority(Roles.Values.ADMIN.name()); //Busca se já existe a role admin no DB
		Roles roleAdmin;
		if (roleAdminOptional == null) {
			roleAdmin = new Roles(Roles.Values.ADMIN.name()); // caso não exista cria a rola admin
			rolesRepository.save(roleAdmin);
		} else {
			roleAdmin = roleAdminOptional;
		}
		
		var userAdmin = userRepository.findByNome("admin");
		
		userAdmin.ifPresentOrElse(
								 (user) -> {System.out.println("Admin já existe"); },
								 () -> {
									 var user = new User();
									 user.setNome("admin");
									 user.setSobrenome("");
									 user.setEmail("admin@exemplo.com.br");
									 user.setIdade(01);
									 user.setFoto("");
									 LocalDate dtNascimento = LocalDate.of(1900, 01, 01);
									 user.setDt_nascimento(dtNascimento);
									 user.setSenha(passwordEncoder.encode("123456"));
									 user.setRoles(Set.of(roleAdmin));
									 userRepository.save(user);
								 }
								 );
	}

}
