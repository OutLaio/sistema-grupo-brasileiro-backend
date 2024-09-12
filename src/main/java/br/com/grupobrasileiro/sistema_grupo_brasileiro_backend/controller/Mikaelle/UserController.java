package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.Mikaelle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle.EmployeeService;
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
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<EmployeeView> updateEmployee(
        @PathVariable Long id, 
        @Valid @RequestBody EmployeeForm form, 
        @AuthenticationPrincipal UserDetails userDetails) {
        
        // Chama o serviço para atualizar o Employee e retorna a resposta
        EmployeeView updatedEmployee = employeeService.updateEmployee(id, form);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

    /**
     * Endpoint para adicionar um novo Employee.
     * Apenas usuários com o papel 'ADMIN' ou o próprio usuário (CLIENT) com o ID correspondente
     * podem criar novos empregados.
     *
     * @param form o formulário EmployeeForm contendo os dados do novo Employee
     * @param userId o ID do usuário associado ao novo Employee
     * @return uma resposta com status 201 Created e o Employee recém-criado
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<Employee> addEmployee(
        @Valid @RequestBody EmployeeForm form, 
        @RequestParam Long userId) {
        
        // Chama o serviço para adicionar um novo Employee e retorna a resposta
        Employee newEmployee = employeeService.addEmployee(form, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }


	/**
     * Endpoint para buscar um Employee pelo ID.
     * @param id o ID do Employee.
     * @return uma resposta com status 200 OK e o Employee encontrado.
     */
	@Cacheable(value = "idEmployee", key = "#id")
	@GetMapping("/employees/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
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
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
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

}
