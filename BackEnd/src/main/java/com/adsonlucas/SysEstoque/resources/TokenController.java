package com.adsonlucas.SysEstoque.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
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
		
		var user = userRepository.findByUsername(loginRequest.username());
		
		 if (user.isEmpty() || user.get().isLoginCorrect) {
			 throw new BadCredentialsException("user is invalid!");
		 }
		 
		 return null;
	}
}
