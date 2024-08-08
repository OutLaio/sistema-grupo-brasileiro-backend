package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResponseForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import jakarta.validation.Valid;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final TokenService tokenService;
    private final UserService userService;
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestForm body) {
        LOGGER.info("Starting login request for: email={}", body.email());

	    	User user = (User) userRepository.findByEmail(body.email());
	        if (user == null) {
	            LOGGER.warn("User not found with email: {}", body.email());
	            throw new EntityNotFoundException("Usuário não encontrado com e-mail: " + body.email());     
	        }
	        
	        if (user.getActive() == false) {
	        	 LOGGER.warn("User is not active: {}", body.email());
		         throw new UserIsNotActiveException("Acesso negado.");  
	        }

            var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            LOGGER.info("Successful authentication for: email={}", body.email());
            return ResponseEntity.ok(new ResponseForm(token));

	}
    @PostMapping("/register")
	public ResponseEntity<?> save(@Valid @RequestBody UserForm body) {
        LOGGER.info("Starting login request for: email={}", body.email());
        	
        try {
            UserView view = userService.save(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(view);
        } catch (EmailUniqueViolationException e) {
            LOGGER.error("Email unique violation for email={}: {}", body.email(), e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Unexpected error during registration for email={}: {}", body.email(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
	

}
