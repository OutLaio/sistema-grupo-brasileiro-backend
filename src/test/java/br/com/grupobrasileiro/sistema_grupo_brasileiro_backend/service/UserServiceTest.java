package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastroCompleto() {
        UserForm userForm = new UserForm("João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor", "123456", "joao.silva@example.com", "ROLE_CLIENT", "password123");

        // Simulate the save operation
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User user = userService.registerUser(userForm);

        assertNotNull(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFalhaNoCadastro() {
        UserForm userForm = new UserForm("", "", "invalid-email", "", "TI", "Developer", "NOP", "USER", "short");

        // Simulate a failure in the save operation
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Dados inválidos"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(userForm);
        });

        assertEquals("Dados inválidos", thrown.getMessage());
    }
}
