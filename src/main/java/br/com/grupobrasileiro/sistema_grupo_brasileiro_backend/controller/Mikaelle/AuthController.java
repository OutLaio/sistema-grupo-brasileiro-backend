package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.Mikaelle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.UserDetailsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle.EmployeeService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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
    @PostMapping("/register-collaborator")
    public ResponseEntity<?> registerCollaborator(@Valid @RequestBody UserDetailsForm form) {

        // Chama UserService para validar e criar o usuário
        User user = userService.createUser(form.userForm());

        // Chama EmployeeService para instanciar e persistir Employee
        employeeService.addEmployee(form.employeeForm(), user);

        // Retorna a View completa com status HTTP 200 OK
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
