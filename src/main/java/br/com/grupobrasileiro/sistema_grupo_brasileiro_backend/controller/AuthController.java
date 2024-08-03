package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResponseForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import jakarta.validation.Valid;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.*;
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

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            LOGGER.info("Successful authentication for: email={}", body.email());
            return ResponseEntity.ok(new ResponseForm(token));
        } catch (Exception e) {
            LOGGER.error("Authentication error for email={}: {}", body.email(), e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
	public ResponseEntity<?> save(@Valid @RequestBody UserForm body) throws Exception {
        LOGGER.info("Starting login request for: email={}", body.email());
        
        try {
            if (this.userRepository.findByEmail(body.email()) != null) {
                LOGGER.warn("Email already in use: {}", body.email());
                return ResponseEntity.status(400).body("Email already in use");
            }
			UserView view = userService.save(body);
		    return ResponseEntity.status(HttpStatus.CREATED).body(view);
        } catch (Exception e) {
            LOGGER.error("Authentication error for email={}: {}", body.email(), e.getMessage());
            return ResponseEntity.status(400).body("Email already in use");
        }
	}
	

}
