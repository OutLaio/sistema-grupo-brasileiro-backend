package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operações relacionadas aos Employees.
 */
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Employee-related operations")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Endpoint para atualizar um Employee existente.
     * Permite que usuários com o papel 'ADMIN' ou o próprio usuário (CLIENT) com o ID correspondente
     * atualizem seus dados.
     *
     * @param id o ID do Employee a ser atualizado
     * @param form o formulário EmployeeForm contendo os dados a serem atualizados
     * @return uma resposta com status 200 OK e o Employee atualizado
     */
    @Operation(summary = "Update Employee", description = "Updates the data of an existing Employee.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeView.class))
        }),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
        @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeView> updateEmployee(
            @Parameter(description = "ID do Employee a ser atualizado") @PathVariable Long id,
            @Valid @RequestBody EmployeeForm form) {
        EmployeeView updatedEmployee = employeeService.updateEmployee(id, form);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }

    /**
     * Endpoint para listar todos os Employees com paginação.
     * 
     * @param page o número da página (padrão 0).
     * @param size o tamanho da página (padrão 10).
     * @param direction a direção da ordenação (ASC ou DESC, padrão ASC).
     * @param orderBy o campo para ordenar (padrão "name").
     * @return Página de EmployeeView com status 200 OK.
     */
    @Operation(summary = "List all Employees", description = "List all Employees with pagination.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de Employees", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
        })
    })
    @Cacheable("Collaborators")
    @GetMapping("/allCollaborators")
    public ResponseEntity<Page<EmployeeView>> getAllCollaborators(
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Direção da ordenação (ASC ou DESC)") @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @Parameter(description = "Campo de ordenação") @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<EmployeeView> employeesPage = employeeService.getAllCollaborators(pageRequest);
        return ResponseEntity.ok(employeesPage);
    }

    /**
     * Endpoint para buscar um Employee pelo ID.
     *
     * @param id o ID do Employee.
     * @return uma resposta com status 200 OK e o Employee encontrado.
     */
    @Operation(summary = "Buscar Employee por ID", description = "Busca um Employee pelo seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Employee found", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeView.class))
        }),
        @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @Cacheable(value = "idEmployee", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeView> getEmployeeById(
            @Parameter(description = "ID do Employee") @PathVariable Long id) {
        EmployeeView employeeView = employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeView);
    }
}
