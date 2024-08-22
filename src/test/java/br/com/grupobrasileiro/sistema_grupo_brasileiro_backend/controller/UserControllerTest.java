package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @InjectMocks
    private UserController userController;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }


    @Test
    @WithMockUser(username = "user", roles = {"CLIENT"})
    void testSaveUser() throws Exception {
        Faker faker = new Faker();

        // Cria um objeto UserForm com dados fictícios gerados pelo Faker
        UserForm userForm = new UserForm(
            faker.name().firstName(), // Nome fictício
            faker.name().lastName(), // Sobrenome fictício
            faker.phoneNumber().phoneNumber(), // Número de telefone fictício
            faker.company().industry(), // Setor fictício
            faker.job().title(), // Ocupação fictícia
            faker.bothify("#####"), // NOP fictício (exemplo)
            faker.internet().emailAddress(), // E-mail fictício
            faker.internet().password(), // Senha fictícia
            RoleEnum.ROLE_CLIENT.getCode() // Código do papel (fixo para o teste)
        );

        // Cria um objeto UserView com dados fictícios
        UserView userView = new UserView(
            1L, // Supondo que o ID retornado será 1
            userForm.name(),
            userForm.lastname(),
            userForm.phonenumber(),
            userForm.sector(),
            userForm.occupation(),
            userForm.nop(),
            userForm.email(),
            userForm.role(),
            true // Status fictício
        );

        // Configura o comportamento do mock do UserService
        when(userService.save(any(UserForm.class))).thenReturn(userView);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andDo(print())
                .andExpect(status().isCreated())
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
    void testGetUserProfile() throws Exception {
        Long userId = faker.number().randomNumber();
        UserProfileView userProfileView = new UserProfileView(
            userId,
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            faker.internet().emailAddress()
        );

        when(userService.getUserProfile(eq(userId))).thenReturn(userProfileView);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userProfileView.id()))
                .andExpect(jsonPath("$.name").value(userProfileView.name()))
                .andExpect(jsonPath("$.lastname").value(userProfileView.lastname()));
    }

    @Test
    void testUpdateUser() throws Exception {
        Long userId = faker.number().randomNumber();
        UpdateUserForm updateUserForm = new UpdateUserForm(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###")
        );

        User user = new User();
        user.setId(userId);
        user.setName(updateUserForm.name());
        user.setLastname(updateUserForm.lastname());

        when(userService.updateUser(eq(userId), any(UpdateUserForm.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"" + updateUserForm.name() + "\", \"lastname\": \"" + updateUserForm.lastname() + "\", \"phonenumber\": \"" + updateUserForm.phonenumber() + "\", \"sector\": \"" + updateUserForm.sector() + "\", \"occupation\": \"" + updateUserForm.occupation() + "\", \"nop\": \"" + updateUserForm.nop() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.lastname").value(user.getLastname()));
    }

    @Test
    void testDeactivateUser() throws Exception {
        Long userId = faker.number().randomNumber();

        User user = new User();
        user.setId(userId);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

        mockMvc.perform(put("/api/v1/users/deactivate")
                .principal(new UsernamePasswordAuthenticationToken(user, null)))
                .andExpect(status().isNoContent());

        verify(userService).deactivateUser(userId);
    }

    @Test
    void testGetUsersByRole() throws Exception {
        Integer role = RoleEnum.ROLE_CLIENT.getCode();
        PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "name");
        Page<UserView> userViewPage = new PageImpl<>(List.of(new UserView(
            faker.number().randomNumber(),
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            faker.internet().emailAddress(),
            role,
            true
        )), pageRequest, 1);

        when(userService.getUsersCollaborators(eq(role), eq(pageRequest))).thenReturn(userViewPage);


        mockMvc.perform(get("/api/v1/users/byRole")
                .param("role", role.toString())
                .param("page", "0")
                .param("direction", "ASC")
                .param("orderBy", "name")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(userViewPage.getContent().get(0).name()))
                .andExpect(jsonPath("$.content[0].lastname").value(userViewPage.getContent().get(0).lastname()));
    }
    
    @Test
    void testValidPhonenumber() throws Exception {
        UserForm userForm = new UserForm("John", "Doe", "+55 (11) 98888-8888", "Tech", "Engineer", "12345", "john.doe@example.com", "Valid1Password@", 1);
        // Check for validation errors for valid phone numbers
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isCreated());
    }

    @Test
    void testInvalidPhonenumber() throws Exception {
        UserForm userForm = new UserForm("John", "Doe", "1234", "Tech", "Engineer", "12345", "john.doe@example.com", "Valid1Password@", 1);
        // Check for validation error for invalid phone numbers
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Invalid phone number"));
    }
    
    @Test
    void testValidPassword() throws Exception {
        UserForm userForm = new UserForm("John", "Doe", "+55 (11) 98888-8888", "Tech", "Engineer", "12345", "john.doe@example.com", "Valid1Password@", 1);
        // Check for validation errors for valid passwords
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isCreated());
    }

    @Test
    void testInvalidPassword() throws Exception {
        UserForm userForm = new UserForm("John", "Doe", "+55 (11) 98888-8888", "Tech", "Engineer", "12345", "john.doe@example.com", "invalidpassword", 1);
        //Check for validation error for invalid passwords
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Password does not meet the required criteria"));
    }
    
    @Test
    void testSaveUserWithDuplicateEmail() throws Exception {
        UserForm userForm = new UserForm(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            faker.internet().emailAddress(),
            faker.internet().password(),
            RoleEnum.ROLE_CLIENT.getCode()
        );

                when(userService.save(any(UserForm.class))).thenThrow(new RuntimeException("Email already exists"));

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }

    @Test
    void testSaveUserWithMissingRequiredFields() throws Exception {
        UserForm userForm = new UserForm(
            null, 
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            faker.internet().emailAddress(),
            faker.internet().password(),
            RoleEnum.ROLE_CLIENT.getCode()
        );

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testSaveUserDatabaseError() throws Exception {
        UserForm userForm = new UserForm(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            faker.internet().emailAddress(),
            faker.internet().password(),
            RoleEnum.ROLE_CLIENT.getCode()
        );

        when(userService.save(any(UserForm.class))).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSaveUserWithSQLInjection() throws Exception {
        UserForm userForm = new UserForm(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            "test@example.com",
            "password' OR '1'='1", // SQL Injection attempt
            RoleEnum.ROLE_CLIENT.getCode()
        );

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isBadRequest()); // Espera-se que falhe na validação
    }

    @Test
    void testSaveUserWithInvalidEmailFormat() throws Exception {
        UserForm userForm = new UserForm(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.bothify("??###"),
            "invalid-email", // Email inválido
            faker.internet().password(),
            RoleEnum.ROLE_CLIENT.getCode()
        );

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userForm)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Invalid email format"));
    }


}
