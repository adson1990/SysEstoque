package com.adsonlucas.SysEstoque.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.adsonlucas.SysEstoque.entities.Roles;
import com.adsonlucas.SysEstoque.entities.User;
import com.adsonlucas.SysEstoque.repositories.RolesRepository;
import com.adsonlucas.SysEstoque.repositories.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import jakarta.transaction.Transactional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO) //a serialização de objetos PageImpl diretamente em JSON não é garantida em termos de estrutura estável. Esse trecho é para garantir estabilidade, compatibilidade e flexibilidade
public class WebSecurityConfig implements CommandLineRunner {

	@Value("${jwt.public.key}")
	private RSAPublicKey publicKey;

	@Value("${jwt.private.key}")
	private RSAPrivateKey privateKey;

	private UserDetailsService userDetailsService;

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String[] PUBLIC = {
	        "/clients/register",
	        "/login/**",
	        "/auth/client/refresh",
	        "/token/consulta",
	        "/token/cliente",
	        "/swagger-ui/**",
	        "/v3/api-docs/**",
	        "/swagger-ui.html",
	        "/swagger-resources/**",
	        "/webjars/**",
	        "/v2/api-docs/**",
	        "/configuration/**"
	    };
	
		
		  @Autowired public WebSecurityConfig(UserDetailsService userDetailsService) {
		  this.userDetailsService = userDetailsService; }
		 

	public WebSecurityConfig( UserDetailsService userDetailsService, 
							PasswordEncoder passwordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

//	@Order(1)
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
        .csrf(csrf -> csrf.disable()) // Desativa CSRF para simplificar os testes
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(PUBLIC).permitAll() // Permite todos os métodos HTTP para endpoints públicos
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .anyRequest().authenticated() // Todas as outras requisições requerem autenticação
        )
        .headers(headers -> headers
            .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Permite frames da mesma origem (necessário para H2 Console)
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(Customizer.withDefaults()) // Configuração padrão para JWT
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Política de sessão sem estado
        );
		
		return http.build();
	}
	
	@Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        return converter;
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
	
	@Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = rolesRepository.findByAuthority(Roles.Values.ADMIN.name());

        if (roleAdmin == null) {
            roleAdmin = new Roles(Roles.Values.ADMIN.name());
            rolesRepository.save(roleAdmin);
        }

        Roles finalRoleAdmin = roleAdmin;

        var userAdmin = userRepository.findByNome("admin");

        userAdmin.ifPresentOrElse(
            (user) -> System.out.println("Admin já existe"),
            () -> {
                var user = new User();
                user.setNome("ADMIN");
                user.setSobrenome("");
                user.setEmail("admin@exemplo.com.br");
                user.setIdade(1);
                user.setFoto("");
                LocalDate dtNascimento = LocalDate.of(1900, 1, 1);
                user.setDt_nascimento(dtNascimento);
                user.setSenha(passwordEncoder.encode("123456"));
                user.setRoles(Set.of(finalRoleAdmin));
                user.setAccountBlok(false);
                userRepository.save(user);
            }
        );
    }
	
	//userdetail para fins de teste de login
   /* @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("123456"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}*/

}
