package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/requestReset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/resetPassword").permitAll()
                        .requestMatchers("/swagger-ui/*", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // Restrição de acesso para Supervisores
                        .requestMatchers(HttpMethod.PUT, "/api/v1/projects/{id}/assignCollaborator").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/employees/allCollaborators").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/approve/supervisor").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/{id}/hasProduction").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/{id}/finish").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/employees/{id}/standby").hasRole("SUPERVISOR")

                        // Restrição de acesso para Colaboradores


                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

}