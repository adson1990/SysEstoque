package com.adsonlucas.SysEstoque.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.adsonlucas.SysEstoque.services.UserService;
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

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) //Todas as requisições devem ser autenticadas.
			.csrf(csrf -> csrf.disable()) //vulnerabilidade proposta para facilitar os testes, nunca subir em produção
			.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) //configuração padrão de autenticação com JWT
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // não precisa guardar nada em sessão
			
			;
		
		return http.build();
	}

    @Bean
    BCryptPasswordEncoder passEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager (HttpSecurity http, 
    											 PasswordEncoder encoder, 
    											 UserService service) throws Exception {
    	
    	return http.getSharedObject(AuthenticationManagerBuilder.class)
    			.userDetailsService(service)
    			.passwordEncoder(encoder)
    			.and().build();
    } 

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build(); // fará a descriptografia quando receber a requisição
    }

    @Bean
    JwtEncoder jwtEncoder() {
    	JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
    	var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    	
    	return new NimbusJwtEncoder(jwks);
    }
	
}
