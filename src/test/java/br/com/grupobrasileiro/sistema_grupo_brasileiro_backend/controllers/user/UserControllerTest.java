package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.user;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user.UserController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Change Password - Success")
    void changePassword_Success() {
        PasswordForm passwordForm = new PasswordForm(1L, "currentPassword", "newPassword");

        ResponseEntity<String> response = userController.changePassword(passwordForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password successfully changed!", response.getBody());
        verify(userService, times(1)).changePassword(eq(passwordForm));
    }

    @Test
    @DisplayName("Deactivate User - Success")
    void deactivateUser_Success() {
        Long userId = 1L;

        ResponseEntity<Void> response = userController.deactivateUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deactivateUser(eq(userId));
    }

    @Test
    @DisplayName("Change Password - Throws Exception")
    void changePassword_ThrowsException() {
        PasswordForm passwordForm = new PasswordForm(1L, "currentPassword", "newPassword");
        doThrow(new RuntimeException("Error changing password")).when(userService).changePassword(any(PasswordForm.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.changePassword(passwordForm);
        });

        assertEquals("Error changing password", exception.getMessage());
        verify(userService, times(1)).changePassword(eq(passwordForm));
    }

    @Test
    @DisplayName("Deactivate User - Throws Exception")
    void deactivateUser_ThrowsException() {
        Long userId = 1L;
        doThrow(new RuntimeException("Error deactivating user")).when(userService).deactivateUser(eq(userId));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.deactivateUser(userId);
        });

        assertEquals("Error deactivating user", exception.getMessage());
        verify(userService, times(1)).deactivateUser(eq(userId));
    }
}