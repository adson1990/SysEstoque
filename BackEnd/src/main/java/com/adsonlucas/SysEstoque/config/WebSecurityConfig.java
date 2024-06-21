package com.adsonlucas.SysEstoque.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
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
	
	private static final String[] PUBLIC = {"/oauth/token"};
	private static final String[] OPERATOR_OR_ADMIN = {"/clients**"};
	private static final String[] ADMIN = {"/users/**"};

	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	// H2
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/h2-console/**").permitAll()
                //.requestMatchers("/admin/**").hasRole("ADMIN")
               // .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login**")
                .permitAll()
            )
            .httpBasic(Customizer.withDefaults()) // Mantém a configuração padrão para httpBasic
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Desabilita frameOptions para o H2 console

        return http.build();
    }

	@Order(1)
	@Bean
	SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/login")
				.permitAll() // permitir todos os tipos de requisição de login
				.anyRequest().authenticated()) // Todas as requisições devem ser autenticadas.
				.csrf(csrf -> csrf.disable()) // vulnerabilidade proposta para facilitar os testes, nunca subir em produção
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // configuração padrão de autenticação com JWT
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // não precisa guardar nada em sessão																												
		;
		return http.build();
	}
	
	@Bean
    SecurityFilterChain securityFilterChainEndPoints(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers(PUBLIC).permitAll()	
                .requestMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll()
                .requestMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")
                .requestMatchers(ADMIN).hasRole("ADMIN")
            .anyRequest().authenticated() 
            )
            .csrf(csrf -> csrf.disable() );

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

	/*
	 * @Bean DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	 * authProvider.setUserDetailsService(userDetailsService);
	 * authProvider.setPasswordEncoder(passwordEncoder); return authProvider; }
	 */
	
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
