package com.adsonlucas.SysEstoque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.adsonlucas.SysEstoque.services.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((authorize) -> authorize
				
				.requestMatchers("/actuator/**").permitAll()
				
				.anyRequest().authenticated()
				);
		
		return http.build();
	}
    
    @Bean
    public PasswordEncoder passEncoder() {
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
	
}
