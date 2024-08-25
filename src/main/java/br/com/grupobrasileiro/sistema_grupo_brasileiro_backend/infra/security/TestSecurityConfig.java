package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


	@Configuration
	@EnableWebSecurity
	public class TestSecurityConfig {

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	            .anyRequest().permitAll()
	            .and()
	            .csrf().disable();
	        return http.build();
	    }
	}
