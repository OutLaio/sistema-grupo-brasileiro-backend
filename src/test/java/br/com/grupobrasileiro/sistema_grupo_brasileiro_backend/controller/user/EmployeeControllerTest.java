package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.user;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user.EmployeeController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    public EmployeeControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Update Employee - Success")
    void updateEmployee_Success() {
        Long employeeId = 1L;
        EmployeeForm form = new EmployeeForm(
                "John",       // name
                "Doe",        // lastname
                "123456789",  // phoneNumber
                "IT",         // sector
                "Developer",  // occupation
                "Main Agency",// agency
                1L            // avatar
        );
        EmployeeView updatedEmployee = new EmployeeView(
                employeeId,    // id
                null,          // userView
                "John",        // name
                "Doe",         // lastname
                "123456789",   // phonenumber
                "IT",          // sector
                "Developer",   // occupation
                "Main Agency", // agency
                1L             // avatar
        );

        when(employeeService.updateEmployee(eq(employeeId), any(EmployeeForm.class))).thenReturn(updatedEmployee);

        ResponseEntity<EmployeeView> response = employeeController.updateEmployee(employeeId, form);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployee, response.getBody());
        verify(employeeService, times(1)).updateEmployee(eq(employeeId), eq(form));
    }

    @Test
    @DisplayName("Update Employee - Throws Exception")
    void updateEmployee_ThrowsException() {
        Long employeeId = 1L;
        EmployeeForm form = new EmployeeForm(
                "John",
                "Doe",
                "123456789",
                "IT",
                "Developer",
                "Main Agency",
                1L
        );
        doThrow(new RuntimeException("Error updating employee")).when(employeeService).updateEmployee(any(Long.class), any(EmployeeForm.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeController.updateEmployee(employeeId, form);
        });

        assertEquals("Error updating employee", exception.getMessage());
        verify(employeeService, times(1)).updateEmployee(eq(employeeId), eq(form));
    }

    @Test
    @DisplayName("Get All Collaborators - Success")
    void getAllCollaborators_Success() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        Page<EmployeeView> employeesPage = new PageImpl<>(Collections.singletonList(new EmployeeView(
                1L,            // id
                null,          // userView
                "John",        // name
                "Doe",         // lastname
                "123456789",   // phonenumber
                "IT",          // sector
                "Developer",   // occupation
                "Main Agency", // agency
                1L             // avatar
        )));

        when(employeeService.getAllCollaborators(any(PageRequest.class))).thenReturn(employeesPage);

        ResponseEntity<Page<EmployeeView>> response = employeeController.getAllCollaborators(0, 10, "ASC", "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeesPage, response.getBody());
        verify(employeeService, times(1)).getAllCollaborators(eq(pageRequest));
    }

    @Test
    @DisplayName("Get Employee By ID - Success")
    void getEmployeeById_Success() {
        Long employeeId = 1L;
        EmployeeView employeeView = new EmployeeView(
                employeeId,    // id
                null,          // userView
                "John",        // name
                "Doe",         // lastname
                "123456789",   // phonenumber
                "IT",          // sector
                "Developer",   // occupation
                "Main Agency", // agency
                1L             // avatar
        );

        when(employeeService.getEmployeeById(eq(employeeId))).thenReturn(employeeView);

        ResponseEntity<EmployeeView> response = employeeController.getEmployeeById(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeView, response.getBody());
        verify(employeeService, times(1)).getEmployeeById(eq(employeeId));
    }

    @Test
    @DisplayName("Get Employee By ID - Throws Exception")
    void getEmployeeById_ThrowsException() {
        Long employeeId = 1L;
        doThrow(new RuntimeException("Employee not found")).when(employeeService).getEmployeeById(eq(employeeId));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeController.getEmployeeById(employeeId);
        });

        assertEquals("Employee not found", exception.getMessage());
        verify(employeeService, times(1)).getEmployeeById(eq(employeeId));
    }
}

