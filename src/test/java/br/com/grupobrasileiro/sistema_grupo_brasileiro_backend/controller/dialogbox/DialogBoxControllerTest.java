package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.dialogbox;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DialogBoxControllerTest {

    @InjectMocks
    private DialogBoxController dialogBoxController;

    @Mock
    private DialogBoxService dialogBoxService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testCreateMessage() {
        // Arrange
        Long idEmployee = faker.number().randomNumber();
        Long idBriefing = faker.number().randomNumber();
        String message = faker.lorem().sentence();

        // Criação do DialogBoxForm
        DialogBoxForm dialogBoxForm = new DialogBoxForm(idEmployee, idBriefing, message);

        // Criação do EmployeeSimpleView
        EmployeeSimpleView employeeSimpleView = new EmployeeSimpleView(
                idEmployee, // ID do funcionário
                faker.name().fullName(), // Nome do funcionário
                faker.number().randomNumber() // Outro campo, se necessário
        );

        // Criação do DialogBoxView
        DialogBoxView dialogBoxView = new DialogBoxView(
                faker.number().randomNumber(), // id
                employeeSimpleView,             // employee
                LocalDateTime.now(),            // time
                message                          // dialog
        );

        // Simulação do comportamento do serviço
        when(dialogBoxService.createMessage(dialogBoxForm)).thenReturn(dialogBoxView);

        // Act
        ResponseEntity<DialogBoxView> response = dialogBoxController.createMessage(dialogBoxForm);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dialogBoxView, response.getBody());
    }
}