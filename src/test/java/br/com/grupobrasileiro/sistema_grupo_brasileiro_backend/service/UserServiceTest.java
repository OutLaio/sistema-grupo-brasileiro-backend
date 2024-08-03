package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFormMapper userFormMapper;

    @Mock
    private UserViewMapper userViewMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_success() throws Exception {
        // Arrange
        UserForm userForm = new UserForm(
                "João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor",
                "123456", "joao.silva@example.com", "Password123!", RoleEnum.CLIENT);
        User user = new User(
                "João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor",
                "123456", "joao.silva@example.com", "Password123!", RoleEnum.CLIENT);
        UserView userView = new UserView(
                null, "João", "Silva", "joao.silva@example.com", null, null, null, null, null);

        // Configuração dos mocks
        when(userFormMapper.map(userForm)).thenReturn(user);
        when(userViewMapper.map(user)).thenReturn(userView);
        when(userRepository.save(user)).thenReturn(user); // Adicionado

        // Act
        UserView result = userService.save(userForm);

        // Assert
        assertNotNull(result);
        assertEquals(userView, result);
        verify(userRepository).save(user);
    }

    @Test
    void saveUser_emailAlreadyRegistered() throws Exception {
        // Arrange
        UserForm userForm = new UserForm(
                "João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor",
                "123456", "joao.silva@example.com", "Password123!", RoleEnum.CLIENT);
        User user = new User(
                "João", "Silva", "+55 (11) 98888-8888", "Tecnologia", "Desenvolvedor",
                "123456", "joao.silva@example.com", "Password123!", RoleEnum.CLIENT);

        // Configuração dos mocks
        when(userFormMapper.map(userForm)).thenReturn(user);
        doThrow(new DataIntegrityViolationException("Duplicate entry")).when(userRepository).save(user);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> userService.save(userForm));
        assertEquals("Email or username already registered", exception.getMessage());
    }
}
