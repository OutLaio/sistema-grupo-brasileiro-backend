package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void testGetUsersCollaborators() throws Exception {
        UserView user1 = new UserView(1L, "John", "Doe", "1234567890", "IT", "Developer", "001", "john.doe@example.com", 1, true);
        UserView user2 = new UserView(2L, "Jane", "Doe", "0987654321", "HR", "Manager", "002", "jane.doe@example.com", 1, true);
        Page<UserView> userPage = new PageImpl<>(Arrays.asList(user1, user2), PageRequest.of(0, 10, Sort.by("name")), 2);

        given(userService.getUsersCollaborators(1, PageRequest.of(0, 10, Sort.by("name")))).willReturn(userPage);

        mockMvc.perform(get("/api/v1/users/collaborators")
                .param("page", "0")
                .param("direction", "ASC")
                .param("orderBy", "name")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("John"))
                .andExpect(jsonPath("$.content[1].name").value("Jane"));
    }

    @Test
    @WithMockUser(roles = {"SUPERVISOR"})
    void testGetUserProfile_AsSupervisor() throws Exception {
        Long userId = 1L;
        UserProfileView userProfile = new UserProfileView(
            userId, faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(),
            faker.company().industry(), faker.job().title(), faker.bothify("#####"), faker.internet().emailAddress()
        );

        when(userService.getUserProfile(userId)).thenReturn(userProfile);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "clientUser", roles = {"CLIENT"})
    void testGetUserProfile_AsClient_OwnProfile() throws Exception {
        Long userId = 1L;
        UserProfileView userProfile = new UserProfileView(
            userId, faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(),
            faker.company().industry(), faker.job().title(), faker.bothify("#####"), faker.internet().emailAddress()
        );

        when(userService.getUserProfile(userId)).thenReturn(userProfile);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void testUpdateUser_AsAdmin() throws Exception {
        Long userId = 1L;
        UpdateUserForm updateUserForm = new UpdateUserForm(
            faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(),
            faker.company().industry(), faker.job().title(), faker.bothify("#####")
        );

        User updatedUser = new User(userId, updateUserForm.name(), updateUserForm.lastname(),
            updateUserForm.phonenumber(), updateUserForm.sector(), updateUserForm.occupation(),
            updateUserForm.nop(), faker.internet().emailAddress(), "encryptedPassword", RoleEnum.ROLE_CLIENT.getCode());

        when(userService.updateUser(eq(userId), any(UpdateUserForm.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(updateUserForm.name()));
    }

    @Test
    @WithMockUser(username = "clientUser", roles = {"CLIENT"})
    void testUpdateUser_AsClient_OwnProfile() throws Exception {
        Long userId = 1L;
        UpdateUserForm updateUserForm = new UpdateUserForm(
            faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(),
            faker.company().industry(), faker.job().title(), faker.bothify("#####")
        );

        User updatedUser = new User(userId, updateUserForm.name(), updateUserForm.lastname(),
            updateUserForm.phonenumber(), updateUserForm.sector(), updateUserForm.occupation(),
            updateUserForm.nop(), faker.internet().emailAddress(), "encryptedPassword", RoleEnum.ROLE_CLIENT.getCode());

        when(userService.updateUser(eq(userId), any(UpdateUserForm.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(updateUserForm.name()));
    }

    @Test
    @WithMockUser(username = "adminUser", roles = {"ADMIN"})
    void testDeactivateUser_AsAdmin() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).deactivateUser(userId);

        mockMvc.perform(put("/api/v1/users/deactivate")
                .with(request -> {
                    request.setUserPrincipal(new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
                    return request;
                }))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "clientUser", roles = {"CLIENT"})
    void testDeactivateUser_AsClient() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).deactivateUser(userId);

        mockMvc.perform(put("/api/v1/users/deactivate")
                .with(request -> {
                    request.setUserPrincipal(new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.createAuthorityList("ROLE_CLIENT")));
                    return request;
                }))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    void testGetUserProfile_AsClient_NotOwnProfile() throws Exception {
        Long userId = 2L; // Different user ID than the authenticated one
        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"SUPERVISOR"})
    void testGetUserProfile_NonExistentUser() throws Exception {
        Long userId = 999L;
        when(userService.getUserProfile(userId)).thenReturn(null);

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    void testUpdateUser_InvalidData() throws Exception {
        Long userId = 1L;
        UpdateUserForm updateUserForm = new UpdateUserForm(
            "", "", "", "", "", ""
        );

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "clientUser", roles = {"CLIENT"})
    void testDeactivateUser_NotAuthenticated() throws Exception {
        mockMvc.perform(put("/api/v1/users/deactivate"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUsersCollaborators_NoAuth() throws Exception {
        mockMvc.perform(get("/api/v1/users/collaborators"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUserProfile_NoAuth() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isUnauthorized());
    }
}
