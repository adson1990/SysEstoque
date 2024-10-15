package com.adsonlucas.SysEstoque.resources;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adsonlucas.SysEstoque.entities.Client;
import com.adsonlucas.SysEstoque.entities.RefreshToken;
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginResponse;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginResponseWithSexId;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenRefreshRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenRefreshResponse;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenTemporarioRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenTemporarioResponse;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;
import com.adsonlucas.SysEstoque.resouces.exceptions.TokenRefreshException;
import com.adsonlucas.SysEstoque.services.ClientService;
import com.adsonlucas.SysEstoque.services.RefreshTokenService;
import com.adsonlucas.SysEstoque.services.UserService;

@RestController
public class LoginController {

	private JwtEncoder jwtEncoder = null;
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@SuppressWarnings("unused")
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClientService clientService;
	
	@SuppressWarnings("unused")
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
    private RefreshTokenService refreshTokenService;
	
	public LoginController(JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder,
						  UserRepository userRepository, RefreshTokenService refreshTokenService) {
		this.jwtEncoder = jwtEncoder;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.refreshTokenService = refreshTokenService;
	}

	@PostMapping("/login/user")
	public ResponseEntity<LoginResponse> loginAdm(@RequestBody LoginRequest loginRequest){	
		Optional<User> user = Optional.ofNullable((User) userService.loadUserByUsername(loginRequest.username()));
		
		if (!bCryptPasswordEncoder.matches(loginRequest.password(), user.get().getPassword())){
			throw new BadCredentialsException("user or password is invalid!");
		}
		 
		 var now = Instant.now();
		 var accessTokenExpiresIn = 300L; // 5 min
		 
		 var scopes = user.get().getRoles()
				 .stream()
				 .map(Roles::getAuthority)
				 .collect(Collectors.joining(" ")); // obter o scopo de permissão do usuário que esta logando no sistema
		 
		 //configuração de atributos do JSON
		 var claims = JwtClaimsSet.builder()
				 	  .issuer("Backend") // quem está gerando o token
				 	  .subject(user.get().getID().toString()) //usuário quem é 
				 	  .issuedAt(now) // data de emissão do token
				 	  .expiresAt(now.plusSeconds(accessTokenExpiresIn)) // tempo de expiração
				 	  .claim("scope", scopes) // obtendo scopo da requisição
				 	  .build();
		 
		 var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // recuperando o token JWT passando os claims
		 
		 var refreshToken = refreshTokenService.createRefreshToken(user.get().getID());
		 
		 return ResponseEntity.ok(new LoginResponse(jwtValue, accessTokenExpiresIn, refreshToken.getToken()));
	}
	
	@PostMapping("/login/client")
	public ResponseEntity<LoginResponseWithSexId> loginClient(@RequestBody LoginRequest loginRequest){	
		Optional<Client> clientOpt = Optional.ofNullable((Client) clientService.loadClientByEmail(loginRequest.username()));
		logger.info("E-mail enviado no request: " + loginRequest.username());
		
		if (clientOpt.isEmpty()) {
	        throw new BadCredentialsException("User not found!");
	    }

	    Client client = clientOpt.get();
		
		if (!bCryptPasswordEncoder.matches(loginRequest.password(), client.getSenha())){
			throw new BadCredentialsException("user or password is invalid!");
		}
		 
		 var now = Instant.now();
		 var accessTokenExpiresIn = 300L; // 5 min
		 
		 //configuração de atributos do JSON
		 var claims = JwtClaimsSet.builder()
				 	  .issuer("Backend") // quem está gerando o token
				 	  .subject(client.getID().toString()) //usuário quem é 
				 	  .issuedAt(now) // data de emissão do token
				 	  .expiresAt(now.plusSeconds(accessTokenExpiresIn)) // tempo de expiração
				 	  .build();
		 
		 var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // recuperando o token JWT passando os claims
		 
		 
		 return ResponseEntity.ok(new LoginResponseWithSexId(jwtValue, accessTokenExpiresIn, 
				 					client.getSexo(), client.getID(), client.getFoto()));
	}
	
	@PostMapping("/auth/refresh")
	@Transactional
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.refreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    var now = Instant.now();
                    var accessTokenExpiresIn = 300L; // 5 min

                    var scopes = user.getRoles()
                            .stream()
                            .map(Roles::getAuthority)
                            .collect(Collectors.joining(" "));

                    var claims = JwtClaimsSet.builder()
                            .issuer("Backend")
                            .subject(user.getID().toString())
                            .issuedAt(now)
                            .expiresAt(now.plusSeconds(accessTokenExpiresIn))
                            .claim("scope", scopes)
                            .build();

                    var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

                    return ResponseEntity.ok(new TokenRefreshResponse(jwtValue, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
	
	@PostMapping("/token/consulta")
	public ResponseEntity<TokenTemporarioResponse> tokenTemporario(@RequestBody TokenTemporarioRequest request) throws UsernameNotFoundException, 
																													   AuthenticationException{
		logger.info("Request for token");
		userService.loadTokenForSearch(request.username());
		 
		 var now = Instant.now();
		 var accessTokenExpiresIn = 30L; // 30 segundos
		 
		
		 var claims = JwtClaimsSet.builder()
				 	  .issuer("Backend") 
				 	  .subject("ADMIN") 
				 	  .issuedAt(now) 
				 	  .expiresAt(now.plusSeconds(accessTokenExpiresIn))
				 	  .build();
		 
		 var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); 
		 
		 logger.info("Token generated");
		 
		 return ResponseEntity.ok(new TokenTemporarioResponse(jwtValue, accessTokenExpiresIn));
	}
}
