package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    /**
     * Endpoint para atualizar um Employee existente.
     * Permite que usuários com o papel 'ADMIN' ou o próprio usuário (CLIENT) com o ID correspondente
     * atualizem seus dados.
     *
     * @param id o ID do Employee a ser atualizado
     * @param form o formulário EmployeeForm contendo os dados a serem atualizados
     * @return uma resposta com status 200 OK e o Employee atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeView> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeForm form) {
        LOGGER.info("Starting update for employeeId={}", id);
        EmployeeView updatedEmployee = employeeService.updateEmployee(id, form);
        LOGGER.info("Successfully updated employeeId={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

    /**
     * Endpoint para listar todos os Employees com paginação.
     * @param page o número da página (padrão 0).
     * @param size o tamanho da página (padrão 10).
     * @param direction a direção da ordenação (ASC ou DESC, padrão ASC).
     * @param orderBy o campo para ordenar (padrão "name").
     * @return Página de EmployeeView com status 200 OK.
     */
    @Cacheable("Collaborators")
    @GetMapping("/allCollaborators")
    public ResponseEntity<Page<EmployeeView>> getAllCollaborators(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        LOGGER.info("Fetching all collaborators with pagination: page={}, size={}, direction={}, orderBy={}",
                page, size, direction, orderBy);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<EmployeeView> employeesPage = employeeService.getAllCollaborators(pageRequest);
        LOGGER.info("Successfully retrieved page {} of collaborators", page);
        return ResponseEntity.ok(employeesPage);
    }

    /**
     * Endpoint para buscar um Employee pelo ID.
     * @param id o ID do Employee.
     * @return uma resposta com status 200 OK e o Employee encontrado.
     */
    @Cacheable(value = "idEmployee", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeView> getEmployeeById(@PathVariable Long id) {
        LOGGER.info("Fetching employee by id={}", id);
        EmployeeView employeeView = employeeService.getEmployeeById(id);
        LOGGER.info("Successfully retrieved employeeId={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeView);
    }
}
