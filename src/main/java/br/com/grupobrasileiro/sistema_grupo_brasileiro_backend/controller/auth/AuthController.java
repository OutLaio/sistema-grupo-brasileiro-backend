package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.auth;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.auth.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.auth.view.TokenView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.UserDetailsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST para autenticação e registro de usuários.
 * Este controlador expõe endpoints relacionados à autenticação e operações
 * de registro de colaboradores.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint para registrar um novo colaborador.
     *
     * <p>Este método recebe os detalhes do usuário e do funcionário através do {@link UserDetailsForm},
     * cria um novo usuário no sistema e, em seguida, persiste os dados do funcionário associados
     * ao usuário recém-criado.</p>
     *
     * <ul>
     *     <li>Chama o {@code UserService} para validar e criar o usuário com base nas credenciais e dados fornecidos.</li>
     *     <li>Chama o {@code EmployeeService} para instanciar e persistir o colaborador associado ao usuário.</li>
     *     <li>Retorna uma resposta com o status HTTP 200 (OK) em caso de sucesso.</li>
     * </ul>
     *
     * @param form o {@link UserDetailsForm} contendo os dados de usuário e funcionário.
     * @return uma resposta HTTP 200 OK em caso de sucesso, ou um erro apropriado se ocorrer alguma validação.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDetailsForm form) {

        // Chama UserService para validar e criar o usuário
        User user = userService.create(form.userForm());

        // Chama EmployeeService para instanciar e persistir Employee
        EmployeeView employeeView = employeeService.addEmployee(form.employeeForm(), user);

        // Retorna a View completa com status HTTP 200 OK
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeView);
    }

    public ResponseEntity<?> signIn(@Valid @RequestBody LoginForm form){
        LOGGER.info("Starting login request for: email={}", form.email());

        User user = (User) userRepository.findByEmail(form.email()).orElseThrow(
                () -> {
                    LOGGER.warn("User not found with email: {}", form.email());
                    throw new EntityNotFoundException("User not found for email: " + form.email());
                }
        );

        if (user.getDisabled()) {
            LOGGER.warn("User is not active: {}", form.email());
            throw new UserIsNotActiveException("Acesso negado.");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(form.email(), form.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        LOGGER.info("Successful authentication for: email={}", form.email());
        return ResponseEntity.ok(new TokenView(token));
    }
}
