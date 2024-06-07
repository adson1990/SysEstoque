package com.adsonlucas.SysEstoque.resources;

import java.time.Instant;

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

import com.adsonlucas.SysEstoque.entitiesDTO.LoginRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginResponse;
import com.adsonlucas.SysEstoque.repositories.UserRepository;

@RestController
public class TokenController {

	private JwtEncoder jwtEncoder = null;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public TokenController(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		
		var user = userRepository.findByNome(loginRequest.username());
		
		 if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) { // verifica se user e pass estão corretos
			 throw new BadCredentialsException("user is invalid!");
		 }
		 
		 var now = Instant.now();
		 var expiresIn = 300L;
		 
		 //configuração de atributos do JSON
		 var claims = JwtClaimsSet.builder()
				 	  .issuer("myBackend") // quem está gerando o token
				 	  .subject(user.get().getID().toString()) //usuário quem é 
				 	  .issuedAt(now) // data de emissão do token
				 	  .expiresAt(now.plusSeconds(expiresIn)) // tempo de expiração
				 	  .build();
		 
		 var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // recuperando o token JWT passando os claims
		 
		 
		 return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
	}
}
