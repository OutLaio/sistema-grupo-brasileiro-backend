package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@SpringBootTest
public class UserConverterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserConverter userConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConverter() {
        
        Long userId1 = 1L;
        Long userId2 = 2L;
        Set<Integer> userIds = new HashSet<>();
        userIds.add(userId1.intValue());
        userIds.add(userId2.intValue());

        User user1 = new User(userId1, "John", "Doe", "1234567890", "IT", "Developer", "nop1", "john.doe@example.com", "password", 1);
        User user2 = new User(userId2, "Jane", "Doe", "0987654321", "HR", "Manager", "nop2", "jane.doe@example.com", "password", 2);

        // Configuração do comportamento do mock
        when(userRepository.findById(userId1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(userId2)).thenReturn(Optional.of(user2));

        // Execução do método a ser testado
        Set<User> result = userConverter.converter(userIds);

        // Verificações
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
        verify(userRepository, times(1)).findById(userId1);
        verify(userRepository, times(1)).findById(userId2);
    }
}
