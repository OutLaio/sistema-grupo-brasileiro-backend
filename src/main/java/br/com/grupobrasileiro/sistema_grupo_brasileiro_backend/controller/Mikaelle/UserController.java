package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.Mikaelle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Mikaelle.form.DisableUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Mikaelle.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle.PasswordService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;


/**
 * Controlador REST para operações relacionadas a usuários e empregados.
 * Expondo endpoints da API sob o caminho "/api/v1/users".
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    // Injeção do serviço EmployeeService
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint para atualizar um Employee existente.
     * Permite que usuários com o papel 'ADMIN' ou o próprio usuário (CLIENT) com o ID correspondente
     * atualizem seus dados.
     *
     * @param id o ID do Employee a ser atualizado
     * @param form o formulário EmployeeForm contendo os dados a serem atualizados
     * @param userDetails informações do usuário autenticado
     * @return uma resposta com status 200 OK e o Employee atualizado
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeView> updateEmployee(
        @PathVariable Long id, 
        @Valid @RequestBody EmployeeForm form, 
        @AuthenticationPrincipal UserDetails userDetails) {
        
        // Chama o serviço para atualizar o Employee e retorna a resposta
        EmployeeView updatedEmployee = employeeService.updateEmployee(id, form);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

	/**
     * Endpoint para buscar um Employee pelo ID.
     * @param id o ID do Employee.
     * @return uma resposta com status 200 OK e o Employee encontrado.
     */
	@Cacheable(value = "idEmployee", key = "#id")
	@GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeView> getEmployeeById(@PathVariable Long id) {
        EmployeeView employeeView = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeView);
    }

	    /**
     * Endpoint para listar todos os Employees com paginação.
     * @param page o número da página (padrão 0).
     * @param size o tamanho da página (padrão 10).
     * @param direction a direção da ordenação (ASC ou DESC, padrão ASC).
     * @param orderBy o campo para ordenar (padrão "name").
     * @return Página de EmployeeView com status 200 OK.
     */
	@Cacheable("Employees")
    @GetMapping("/employees/all")
    public ResponseEntity<Page<EmployeeView>> getAllEmployees(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        
        // Cria um PageRequest com ordenação
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        
        // Obtém a página de EmployeeView
        Page<EmployeeView> employeesPage = employeeService.getAllEmployees(pageRequest);
        
        return ResponseEntity.ok(employeesPage);
    }

    /**
     * Redefine a senha de um usuário.
     *
     * @param passwordForm o formulário PasswordForm contendo a nova senha e o ID do usuário
     * @return uma resposta com status 200 OK e uma mensagem de sucesso
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordForm passwordForm) {

        // Chama o serviço para redefinir a senha usando o token e a nova senha
        passwordService.resetPassword(passwordForm);

        return ResponseEntity.ok("Password successfully changed!");
    }

    /**
     * Desativa um usuário no sistema.
     * Apenas administradores ou o próprio usuário podem realizar essa ação.
     * 
     * @param disableUserForm formulário contendo o ID do usuário a ser desativado.
     * @return resposta com status 204 No Content se a operação for bem-sucedida.
     */
    @PutMapping("/users/deactivate")
    public ResponseEntity<Void> deactivateUser(@Valid @RequestBody DisableUserForm disableUserForm) {
        userService.deactivateUser(disableUserForm);
        return ResponseEntity.noContent().build();
    }
}
