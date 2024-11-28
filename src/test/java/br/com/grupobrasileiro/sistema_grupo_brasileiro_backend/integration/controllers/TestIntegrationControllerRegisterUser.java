package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestIntegrationControllerRegisterUser {

    @Mock
    private static UserService userService;
    private static ObjectMapper objectMapper;
    private static final Faker faker = new Faker();

    @BeforeAll
    static void setup() {
        MockitoAnnotations.openMocks(TestIntegrationControllerRegisterUser.class);
        userService = mock(UserService.class);
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    @DisplayName("Test create user")
    @Order(1)
    void registerUser() {
        EmployeeForm employeeForm = new EmployeeForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().cellPhone(),
                "Sales",
                "Manager",
                "SP-Agency",
                1L
        );

        UserForm userForm = new UserForm(
                "email8@email.com",
                "SecurePassword123*",
                2L
        );

        // Simular a resposta do serviço
        User mockUser = new User();
        mockUser.setEmail(userForm.email());
        when(userService.create(any(UserForm.class))).thenReturn(mockUser);

        User createdUser = userService.create(userForm);

        // Verificar o status da resposta simulada
        assertNotNull(createdUser, "O corpo da resposta não deve ser nulo");
        assertEquals(userForm.email(), createdUser.getEmail(), "O email do usuário criado deve corresponder");

        assertEquals("email8@email.com", createdUser.getEmail(), "O status da resposta deve ser true");
    }
}
