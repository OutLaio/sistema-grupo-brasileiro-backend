package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;

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
        UserForm userForm = new UserForm(
            faker.name().firstName(), 
            faker.name().lastName(), 
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(), 
            faker.job().title(), 
            faker.bothify("#####"), 
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
                .content(new ObjectMapper().writeValueAsString(updateUserForm)))
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
        
        UserView userView = new UserView(
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
        );

        Page<UserView> userViewPage = new PageImpl<>(List.of(userView), pageRequest, 1);

        when(userService.getUsersCollaborators(eq(role), eq(pageRequest))).thenReturn(userViewPage);

        mockMvc.perform(get("/api/v1/users/collaborators")
                .param("role", role.toString())
                .param("page", "0")
                .param("direction", "ASC")
                .param("orderBy", "name")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value(userViewPage.getContent().get(0).name()))
                .andExpect(jsonPath("$.content[0].lastname").value(userViewPage.getContent().get(0).lastname()));
    }

    @Test
    void testInvalidPhoneNumber() throws Exception {
        UserForm userForm = new UserForm(
            "John",
            "Doe",
            "1234", 
            "Tech",
            "Engineer",
            "12345",
            "john.doe@example.com",
            "Valid1Password@",
            1
        );

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
            .andExpect(status().isBadRequest()) 
            .andExpect(jsonPath("$.errors[0]").value("Invalid phonenumber!")); 
    }

    @Test
    void testValidPassword() throws Exception {
        UserForm userForm = new UserForm("John", "Doe", "+55 (11) 98888-8888", "Tech", "Engineer", "12345", "john.doe@example.com", "Valid1Password@", 1);
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isCreated());
    }

    @Test
    void testInvalidPassword() throws Exception {
        UserForm userForm = new UserForm("John", "Doe", "+55 (11) 98888-8888", "Tech", "Engineer", "12345", "john.doe@example.com", "invalidpassword", 1);
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userForm)))
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
                .content(objectMapper.writeValueAsString(userForm)))
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
                .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Name is required"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeactivateUserWithAdminRole() throws Exception {
        Long userId = faker.number().randomNumber();

        // Mock de desativação de usuário
        doNothing().when(userService).deactivateUser(userId);

        mockMvc.perform(put("/api/v1/users/deactivate")
                .principal(new UsernamePasswordAuthenticationToken(userId, null)))
                .andExpect(status().isNoContent());

        verify(userService).deactivateUser(userId);
    }

    
    
    @Test
    @WithMockUser(username = "client", roles = {"CLIENT"})
    void testDeactivateUserWithClientRole() throws Exception {
        Long userId = faker.number().randomNumber();

        // Mock de desativação de usuário
        doNothing().when(userService).deactivateUser(userId);

        mockMvc.perform(put("/api/v1/users/deactivate")
                .principal(new UsernamePasswordAuthenticationToken(userId, null)))
                .andExpect(status().isNoContent());

        verify(userService).deactivateUser(userId);
    }

    @Test
    @WithMockUser(username = "unauthorizedUser", roles = {"USER"})
    void testDeactivateUserWithInsufficientPermissions() throws Exception {
        mockMvc.perform(put("/api/v1/users/deactivate"))
                .andExpect(status().isForbidden());
    }


}
