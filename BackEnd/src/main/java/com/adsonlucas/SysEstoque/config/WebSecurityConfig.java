package com.adsonlucas.SysEstoque.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey;

	@Value("${jwt.private.key}")
	private RSAPrivateKey privateKey;

	private UserDetailsService userDetailsService;

	@Lazy // inicializa apenas quando for realmente necessária, quebrando a referência circular.
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final String[] PUBLIC = {"/login"};
	private static final String[] TESTE_INSERTS= {"/users","/clients","/products"};
	private static final String[] TESTE_BUSCA= {"/users**","/clients**","/products**","/roles**","/categorie/**"};
	private static final String[] TESTE_UPD_DEL= {"/users/**","/clients/**","/products/**"};

	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

//	@Order(1)
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.POST, PUBLIC).permitAll() // permitir todos os tipos de requisição de login
				.requestMatchers(HttpMethod.POST, TESTE_INSERTS).permitAll()
				.requestMatchers(HttpMethod.GET, TESTE_BUSCA).permitAll()
				.requestMatchers(HttpMethod.PUT, TESTE_UPD_DEL).permitAll()
				.requestMatchers(HttpMethod.DELETE, TESTE_UPD_DEL).permitAll()
				.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.anyRequest().authenticated()) // Todas as requisições devem ser autenticadas.	
				.csrf(csrf -> csrf.disable()) // vulnerabilidade proposta para facilitar os testes, nunca subir em produção
				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions
								.sameOrigin())) //permitir frames de mesma origem para que o console H2 possa abrir
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // configuração padrão de autenticação com JWT
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // não precisa guardar nada em sessão																												
		;
		return http.build();
	}
	
	@Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Customize the JWT converter if needed
        return converter;
	}

	//userdetail para fins de teste de login
    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("123456"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
	
	@Bean
	BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.publicKey).build(); // fará a descriptografia quando receber a requisição
	}

	@Bean
	JwtEncoder jwtEncoder() { // criptografa as requisições
		JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

		return new NimbusJwtEncoder(jwks);
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}
