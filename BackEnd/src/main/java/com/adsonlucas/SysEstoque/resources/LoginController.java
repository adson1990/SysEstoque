package com.adsonlucas.SysEstoque.resources;

import java.time.Duration;
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
import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginResponse;
import com.adsonlucas.SysEstoque.entitiesDTO.LoginResponseWithSexId;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenRefreshRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenRefreshResponse;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenRequest;
import com.adsonlucas.SysEstoque.entitiesDTO.TokenResponse;
import com.adsonlucas.SysEstoque.repositories.ClientRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;
import com.adsonlucas.SysEstoque.resouces.exceptions.TokenRefreshException;
import com.adsonlucas.SysEstoque.services.ClientService;
import com.adsonlucas.SysEstoque.services.RefreshTokenService;
import com.adsonlucas.SysEstoque.services.UserService;

@RestController
public class LoginController {

	private JwtEncoder jwtEncoder = null;

	//private static Logger loggerUser = LoggerFactory.getLogger(UserService.class);
	private static Logger loggerClient = LoggerFactory.getLogger(ClientService.class);
	
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
	/*Gerar token válido para login dos usuários no sistema **/
	public ResponseEntity<LoginResponse> loginAdm(@RequestBody LoginRequest loginRequest) {
		Optional<User> user = Optional.ofNullable((User) userService.loadUserByUsername(loginRequest.username()));

		if (!bCryptPasswordEncoder.matches(loginRequest.password(), user.get().getPassword())) {
			throw new BadCredentialsException("user or password is invalid!");
		}

		var now = Instant.now();
		var accessTokenExpiresIn = Duration.ofMinutes(10).toMillis(); // 10 min

		var scopes = user.get().getRoles().stream().map(Roles::getAuthority).collect(Collectors.joining(" ")); // obter o escopo de permissão do usuário que estpa logando no sistema

		// configuração de atributos do JSON
		var claims = JwtClaimsSet.builder().issuer("Backend") // quem está gerando o token
				.subject(user.get().getID().toString()) // usuário quem é
				.issuedAt(now) // data de emissão do token
				.expiresAt(now.plusSeconds(accessTokenExpiresIn)) // tempo de expiração
				.claim("scope", scopes) // obtendo scopo da requisição
				.build();

		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // recuperando o token JWT passando os claims

		var refreshToken = refreshTokenService.createRefreshToken(user.get().getID());

		return ResponseEntity.ok(new LoginResponse(jwtValue, accessTokenExpiresIn, refreshToken.getRefreshToken()));
	}

	@PostMapping("/login/client")
	/*Gerar token válido para login dos clientes no sistema **/
	public ResponseEntity<LoginResponseWithSexId> loginClient(@RequestBody LoginRequest loginRequest) {
		Optional<Client> clientOpt = Optional
				.ofNullable((Client) clientService.loadClientByEmail(loginRequest.username()));
		loggerClient.info("E-mail enviado no request: " + loginRequest.username());

		if (clientOpt.isEmpty()) {
			throw new BadCredentialsException("User not found!");
		}

		Client client = clientOpt.get();

		if (!bCryptPasswordEncoder.matches(loginRequest.password(), client.getSenha())) {
			throw new BadCredentialsException("Ivalid Password!");
		}

		var now = Instant.now();
		var accessTokenExpiresIn = Duration.ofMinutes(5).toMillis(); // 5 min
		//var accessTokenExpiresIn = Duration.ofSeconds(30).toMillis(); // 30 seg para teste

		var claims = JwtClaimsSet.builder().issuer("Backend") 
				.subject(client.getID().toString()) 
				.issuedAt(now) 
				.expiresAt(now.plusSeconds(accessTokenExpiresIn)) 
				.build();

		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
																								
		var refreshToken = refreshTokenService.createClientRefreshToken(client.getID()); // criando o refresh Token

		return ResponseEntity.ok(new LoginResponseWithSexId(jwtValue, accessTokenExpiresIn, refreshToken.getRefreshToken(),
				client.getSexo(), client.getID(), client.getFoto()));
	}

	@PostMapping("/auth/client/refresh")
	@Transactional
	/*Gerar um novo token de acesso para o cliente caso o refresh token seja válido **/
	public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.refreshToken();

		return refreshTokenService.findByRefreshToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(refreshToken -> {
					var client = refreshToken.getClient();
					var now = Instant.now();
					var accessTokenExpiresIn = Duration.ofMinutes(5).toMillis(); // 5 min
					//var accessTokenExpiresIn = Duration.ofSeconds(30).toMillis(); // 30 seg para teste
					
					// Renova o refresh token
					var clientRefreshToken = refreshTokenService.createClientRefreshToken(client.getID());

					// Gera um novo access token
					//var scopes = client.getRoles().stream().map(Roles::getAuthority).collect(Collectors.joining(" "));

					var claims = JwtClaimsSet.builder().issuer(client.getName()).subject(client.getID().toString()).issuedAt(now)
							.expiresAt(now.plusSeconds(accessTokenExpiresIn)).build();

					var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

					return ResponseEntity.ok(new TokenRefreshResponse(jwtValue, accessTokenExpiresIn, clientRefreshToken.getRefreshToken()));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token expired please login again!"));
	}

	@PostMapping("/token/consulta")
	/*Gerar um token breve para pequenas consultas **/
	public ResponseEntity<TokenResponse> tokenTemporario(@RequestBody TokenRequest request)
			throws UsernameNotFoundException, AuthenticationException {
		loggerClient.info("User request for temporary token");
		clientService.loadClientByEmail(request.emailClient());

		var now = Instant.now();
		var accessTokenExpiresIn = Duration.ofSeconds(30).toMillis(); // 30 segundos

		var claims = JwtClaimsSet.builder().issuer("Backend").subject(request.emailClient()).issuedAt(now)
				.expiresAt(now.plusSeconds(accessTokenExpiresIn)).build();

		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

		loggerClient.info("Token generated");

		return ResponseEntity.ok(new TokenResponse(jwtValue, accessTokenExpiresIn, ""));
	}

	@PostMapping("/token/cliente")
	// Cria token e refresh só com o e-mail após já ter sido validado o cliente conectado
	public ResponseEntity<TokenResponse> requestClientToken(@RequestBody TokenRequest request)
			throws UsernameNotFoundException, AuthenticationException {
		var email = request.emailClient();
		
		loggerClient.info("User request for token: " + email);
		Client client = clientService.loadClientByEmail(email);

		var now = Instant.now();
		var accessTokenExpiresIn = Duration.ofMinutes(5).toMillis(); // 5 minutos 

		var claims = JwtClaimsSet.builder().issuer("Backend").subject(email).issuedAt(now)
				.expiresAt(now.plusSeconds(accessTokenExpiresIn)).build();

		var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

		
		boolean tokenDeleted = refreshTokenService.deleteClientRefreshToken(client.getID());
		
		if (!tokenDeleted) {
			loggerClient.warn("Não foi possível deletar o refresh token anterior para o cliente com ID: " + client.getID());
	        throw new IllegalStateException("Falha ao deletar token antigo. Novo refresh token não foi criado.");
		}
		
		var refreshToken = refreshTokenService.createClientRefreshToken(client.getID());

		loggerClient.info("Token generated only with email" + request);

		return ResponseEntity.ok(new TokenResponse(jwtValue, accessTokenExpiresIn, refreshToken.getRefreshToken()));
	}
}
