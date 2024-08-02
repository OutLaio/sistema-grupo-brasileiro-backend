package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
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
        UserForm userForm = new UserForm("Renata", "Moreno", "renata@example.com", "123456789", "TI", "Developer", "NOP", "USER", "password123");

        when(userRepository.save(any(User.class))).thenReturn(new User());

        User user = userService.registerUser(userForm);

        assertNotNull(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFalhaNoCadastro() {
        UserForm userForm = new UserForm("", "", "invalid-email", "", "TI", "Developer", "NOP", "USER", "short");

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Invalid data"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(userForm);
        });

        assertEquals("Invalid data", thrown.getMessage());
    }
}
