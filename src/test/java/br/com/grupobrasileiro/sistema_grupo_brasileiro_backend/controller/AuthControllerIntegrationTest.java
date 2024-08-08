package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResponseForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserService userService;

    @Test
    public void testSuccessfulLogin() throws Exception {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User(); 
        user.setActive(true);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(new UsernamePasswordAuthenticationToken(user, password));
        when(tokenService.generateToken(user)).thenReturn("fakeToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType("application/json")
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("fakeToken"));
    }


    @Test
    public void testLoginUserNotFound() throws Exception {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        when(userRepository.findByEmail(email)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType("application/json")
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Usuário não encontrado com e-mail: " + email));
    }


    @Test
    public void testLoginUserNotActive() throws Exception {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(false);
        when(userRepository.findByEmail(email)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType("application/json")
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string("Acesso negado."));
        		
        
    }

    @Test
    public void testAuthenticationError() throws Exception {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(true);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType("application/json")
                .content("{\"email\":\"" + email + "\",\"password\":\"wrongpassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Unauthorized"));
    }


    @Test
    public void testSuccessfulRegistration() throws Exception {
        Faker faker = new Faker();
        UserForm userForm = new UserForm(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.phoneNumber().phoneNumber(), 
                faker.company().industry(), 
                faker.job().title(), 
                faker.number().digits(5), 
                faker.internet().emailAddress(), 
                faker.internet().password(), 
                RoleEnum.ROLE_CLIENT.getCode(), 
                true
        );
        UserView userView = new UserView(
                1L, 
                userForm.name(), 
                userForm.lastname(), 
                userForm.phonenumber(), 
                userForm.sector(), 
                userForm.occupation(), 
                userForm.nop(), 
                userForm.email(), 
                userForm.role(), 
                userForm.status()
        ); 
        when(userService.save(userForm)).thenReturn(userView);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType("application/json")
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\",\"status\":\"" + userForm.status() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userForm.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(userForm.lastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value(userForm.phonenumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sector").value(userForm.sector()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value(userForm.occupation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nop").value(userForm.nop()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userForm.email()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userForm.role()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(userForm.status()));
    }

    @Test
    public void testRegistrationEmailUniqueViolation() throws Exception {
        Faker faker = new Faker();
        UserForm userForm = new UserForm(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.phoneNumber().phoneNumber(), 
                faker.company().industry(), 
                faker.job().title(), 
                faker.number().digits(5), 
                faker.internet().emailAddress(), 
                faker.internet().password(), 
                RoleEnum.ROLE_CLIENT.getCode(), 
                true
        );
        when(userService.save(userForm)).thenThrow(new EmailUniqueViolationException("Email já em uso"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType("application/json")
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\",\"status\":\"" + userForm.status() + "\"}"))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("Email já em uso"));
    }

    @Test
    public void testRegistrationUnexpectedError() throws Exception {
        Faker faker = new Faker();
        UserForm userForm = new UserForm(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.phoneNumber().phoneNumber(), 
                faker.company().industry(), 
                faker.job().title(), 
                faker.number().digits(5), 
                faker.internet().emailAddress(), 
                faker.internet().password(), 
                RoleEnum.ROLE_CLIENT.getCode(), 
                true
        );
        when(userService.save(userForm)).thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType("application/json")
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\",\"status\":\"" + userForm.status() + "\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Erro inesperado"));
    }
}

