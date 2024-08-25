package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


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

    private Faker faker;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
        objectMapper = new ObjectMapper(); 
    }


    @Test
    public void testSuccessfulLogin() throws Exception {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(true);

        // Mock userRepository to return a User that implements UserDetails
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(new UsernamePasswordAuthenticationToken(user, password));
        when(tokenService.generateToken(user)).thenReturn("fakeToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("fakeToken"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        // Mock userRepository to return null (or handle as an empty Optional if implemented)
        when(userRepository.findByEmail(email)).thenThrow(new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado com e-mail: " + email));
    }

    @Test
    public void testLoginUserNotActive() throws Exception {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(false);

        // Mock userRepository to return an inactive user
        when(userRepository.findByEmail(email)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Acesso negado."));
    }

    @Test
    public void testAuthenticationError() throws Exception {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(true);

        // Mock userRepository to return an active user
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthorized"));
    }

    @Test
    public void testSuccessfulRegistration() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.number().digits(5),
                faker.internet().emailAddress(),
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
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
                true
        );

        when(userService.save(userForm)).thenReturn(userView);

        String jsonRequest = objectMapper.writeValueAsString(userForm);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userForm.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(userForm.lastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value(userForm.phonenumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sector").value(userForm.sector()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value(userForm.occupation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nop").value(userForm.nop()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userForm.email()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userForm.role()));

        verify(userService).save(userForm); 
    }

    @Test
    public void testRegistrationEmailUniqueViolation() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.number().digits(5),
                faker.internet().emailAddress(),
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );
        
        when(userService.save(userForm)).thenThrow(new EmailUniqueViolationException("Email já em uso"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\"}"))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email já em uso"));
    }

    @Test
    public void testRegistrationUnexpectedError() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.number().digits(5),
                faker.internet().emailAddress(),
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );

        when(userService.save(userForm)).thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }
    
    @Test
    public void testLoginBadCredentials() throws Exception {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(true);

       
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthorized"));
    }
    @Test
    public void testRegistrationMissingEmail() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.number().digits(5),
                null,  // Email ausente
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\"}"))
                .andExpect(status().isBadRequest())  
                .andExpect(content().string("Email é obrigatório"));
    }

    @Test
    public void testRegistrationMissingField() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.number().digits(5),
                null,  // Email ausente
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\"}"))
                .andExpect(status().isBadRequest()) 
                .andExpect(content().string("Email é obrigatório"));
    }
    
    @Test
    public void testRegistrationInvalidEmail() throws Exception {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.number().digits(5),
                "invalid-email",  // Email inválido
                faker.internet().password(),
                RoleEnum.ROLE_CLIENT.getCode()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + userForm.name() + "\",\"lastname\":\"" + userForm.lastname() + "\",\"phonenumber\":\"" + userForm.phonenumber() + "\",\"sector\":\"" + userForm.sector() + "\",\"occupation\":\"" + userForm.occupation() + "\",\"nop\":\"" + userForm.nop() + "\",\"email\":\"" + userForm.email() + "\",\"password\":\"" + userForm.password() + "\",\"role\":\"" + userForm.role() + "\"}"))
                .andExpect(status().isBadRequest())  // Ajuste dependendo da validação específica
                .andExpect(content().string("Email inválido"));
    }

    @Test
    public void testLoginUserIsNotActive() throws Exception {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        User user = new User();
        user.setActive(false);

        when(userRepository.findByEmail(email)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Acesso negado."));
    }

    @Test
    public void testRegisterUnexpectedError() throws Exception {
        // Arrange
        UserForm userForm = new UserForm(
                "John", 
                "Doe", 
                "123456789", 
                "IT", 
                "Developer", 
                "12345", 
                "john.doe@example.com", 
                "password123", 
                RoleEnum.ROLE_CLIENT.getCode()
        );

        when(userService.save(any(UserForm.class))).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }
    
    @Test
    public void testRegisterSuccess() throws Exception {
        // Arrange
        UserForm userForm = new UserForm(
                "John", 
                "Doe", 
                "123456789", 
                "IT", 
                "Developer", 
                "12345", 
                "john.doe@example.com", 
                "password123", 
                RoleEnum.ROLE_CLIENT.getCode()
        );

        UserView userView = new UserView(
                1L, // id fictício
                "John", 
                "Doe", 
                "123456789", 
                "IT", 
                "Developer", 
                "12345", 
                "john.doe@example.com", 
                RoleEnum.ROLE_CLIENT.getCode(), 
                true // status fictício
        );

        when(userService.save(any(UserForm.class))).thenReturn(userView);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userView.id()))
                .andExpect(jsonPath("$.name").value(userView.name()))
                .andExpect(jsonPath("$.lastname").value(userView.lastname()))
                .andExpect(jsonPath("$.phonenumber").value(userView.phonenumber()))
                .andExpect(jsonPath("$.sector").value(userView.sector()))
                .andExpect(jsonPath("$.occupation").value(userView.occupation()))
                .andExpect(jsonPath("$.nop").value(userView.nop()))
                .andExpect(jsonPath("$.email").value(userView.email()))
                .andExpect(jsonPath("$.role").value(userView.role()))
                .andExpect(jsonPath("$.status").value(userView.status()));
    }

    @Test
    public void testSuccessfulUserCreation() throws Exception {
        // Dados de teste
        UserForm userForm = new UserForm(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(), 
                faker.job().title(), 
                faker.idNumber().valid(),
                faker.internet().emailAddress(), 
                faker.internet().password(), 
                1
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
                true
        );

        // Configura o mock do userService
        when(userService.save(any(UserForm.class))).thenReturn(userView);

        // Executa a chamada ao endpoint e verifica a resposta
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userView.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(userView.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(userView.lastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value(userView.phonenumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sector").value(userView.sector()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.occupation").value(userView.occupation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nop").value(userView.nop()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userView.email()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userView.role()));

        verify(userService).save(userForm);
    }

    @Test
    public void testEmailUniqueViolation() throws Exception {
        // Dados de teste
        UserForm userForm = new UserForm(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(), 
                faker.job().title(), 
                faker.idNumber().valid(),
                faker.internet().emailAddress(), 
                faker.internet().password(), 
                1
        );

        // Configura o mock do userService para lançar uma exceção
        when(userService.save(any(UserForm.class)))
                .thenThrow(new EmailUniqueViolationException("Email já em uso"));

        // Executa a chamada ao endpoint e verifica a resposta
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("Email já em uso"));

        verify(userService).save(userForm);
    }

    @Test
    public void testUnexpectedError() throws Exception {
        // Dados de teste
        UserForm userForm = new UserForm(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(), 
                faker.job().title(), 
                faker.idNumber().valid(),
                faker.internet().emailAddress(), 
                faker.internet().password(), 
                1
        );

        // Configura o mock do userService para lançar uma exceção inesperada
        when(userService.save(any(UserForm.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Executa a chamada ao endpoint e verifica a resposta
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Internal server error"));

        verify(userService).save(userForm);
    }
}