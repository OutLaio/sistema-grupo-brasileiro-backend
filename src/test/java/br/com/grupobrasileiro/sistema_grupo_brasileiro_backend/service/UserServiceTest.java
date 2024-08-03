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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

public class UserServiceTest {

        @Test
        public void testUserGettersAndSetters() {
                User user = new User();

                user.setId(1L);
                user.setName("João");
                user.setLastname("Silva");
                user.setPhonenumber("+55 (11) 98888-8888");
                user.setSector("Tecnologia");
                user.setOccupation("Desenvolvedor");
                user.setNop("123456");
                user.setEmail("joao.silva@example.com");
                user.setPassword("senhaSegura");
                user.setRole(RoleEnum.ROLE_CLIENT.getCode());

                assertEquals(1L, user.getId());
                assertEquals("João", user.getName());
                assertEquals("Silva", user.getLastname());
                assertEquals("+55 (11) 98888-8888", user.getPhonenumber());
                assertEquals("Tecnologia", user.getSector());
                assertEquals("Desenvolvedor", user.getOccupation());
                assertEquals("123456", user.getNop());
                assertEquals("joao.silva@example.com", user.getEmail());
                assertEquals(RoleEnum.ROLE_CLIENT.getCode(), user.getRole());
        }
}
