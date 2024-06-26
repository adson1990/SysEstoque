package com.adsonlucas.SysEstoque.resources;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginResponse;
import com.adsonlucas.SysEstoque.repositories.UserRepository;

@RestController
public class LoginController {

	private JwtEncoder jwtEncoder = null;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public LoginController(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder,
						  UserRepository userRepository) {
		this.jwtEncoder = jwtEncoder;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
	}


	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		
		var user = userRepository.findByNome(loginRequest.username());
		
		 if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) { // verifica se user e pass estão corretos
			 throw new BadCredentialsException("user or password is invalid!");
		 }
		 
		 var now = Instant.now();
		 var expiresIn = 300L;
		 
		 var scopes = user.get().getRoles()
				 .stream()
				 .map(Roles::getAuthority)
				 .collect(Collectors.joining(" ")); // obter o scopo de permissão do usuário que esta logando no sistema
		 
		 //configuração de atributos do JSON
		 var claims = JwtClaimsSet.builder()
				 	  .issuer("Backend") // quem está gerando o token
				 	  .subject(user.get().getID().toString()) //usuário quem é 
				 	  .issuedAt(now) // data de emissão do token
				 	  .expiresAt(now.plusSeconds(expiresIn)) // tempo de expiração
				 	  .claim("scope", scopes) // obtendo scopo da requisição
				 	  .build();
		 
		 var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // recuperando o token JWT passando os claims
		 
		 
		 return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
	}
}
