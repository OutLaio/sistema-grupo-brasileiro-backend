package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserProfileViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFormMapper userFormMapper;

    @Mock
    private UserViewMapper userViewMapper;

    @Mock
    private UserProfileViewMapper userProfileViewMapper;

    @InjectMocks
    private UserService userService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void testSaveUser_Success() throws EmailUniqueViolationException {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.bothify("??###"),
                faker.internet().emailAddress(),
                "Password123!",
                1
        );

        User user = new User();
        user.setEmail(userForm.email());

        when(userRepository.findByEmail(userForm.email())).thenReturn(null);
        when(userFormMapper.map(userForm)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserView userView = new UserView(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getPhonenumber(),
            user.getSector(),
            user.getOccupation(),
            user.getNop(),
            user.getEmail(),
            user.getRole(),
            user.isEnabled()
        );
        when(userViewMapper.map(user)).thenReturn(userView);

        UserView result = userService.save(userForm);

        assertNotNull(result);
        assertEquals(userView.id(), result.id());
        assertEquals(userView.name(), result.name());
        assertEquals(userView.lastname(), result.lastname());
        assertEquals(userView.phonenumber(), result.phonenumber());
        assertEquals(userView.sector(), result.sector());
        assertEquals(userView.occupation(), result.occupation());
        assertEquals(userView.nop(), result.nop());
        assertEquals(userView.email(), result.email());
        assertEquals(userView.role(), result.role());
        assertEquals(userView.status(), result.status());

        verify(userRepository).save(user); // Check that the save method was called
    }

    @Test
    void testSaveUser_EmailAlreadyExists() {
        UserForm userForm = new UserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.bothify("??###"),
                faker.internet().emailAddress(),
                "Password123!",
                1
        );

        when(userRepository.findByEmail(userForm.email())).thenReturn(new User());

        assertThrows(EmailUniqueViolationException.class, () -> {
            userService.save(userForm);
        });

        verify(userRepository, never()).save(any(User.class)); // Check that save was not called
    }

    @Test
    void testGetUserProfile_Success() {
        Long userId = faker.number().randomNumber();
        User user = new User();
        user.setId(userId);
        user.setName(faker.name().firstName());
        user.setLastname(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPhonenumber(faker.phoneNumber().phoneNumber());
        user.setSector(faker.company().industry());
        user.setOccupation(faker.job().title());
        user.setNop(faker.bothify("??###"));
        user.setRole(1);
        user.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserProfileView userProfileView = new UserProfileView(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getPhonenumber(),
            user.getSector(),
            user.getOccupation(),
            user.getNop(),
            user.getEmail()
        );
        when(userProfileViewMapper.map(user)).thenReturn(userProfileView);

        UserProfileView result = userService.getUserProfile(userId);

        assertNotNull(result);
        assertEquals(userProfileView.id(), result.id());
        assertEquals(userProfileView.name(), result.name());
        assertEquals(userProfileView.lastname(), result.lastname());
        assertEquals(userProfileView.phonenumber(), result.phonenumber());
        assertEquals(userProfileView.sector(), result.sector());
        assertEquals(userProfileView.occupation(), result.occupation());
        assertEquals(userProfileView.nop(), result.nop());
        assertEquals(userProfileView.email(), result.email());

        verify(userRepository).findById(userId); // Verify that findById was called
    }

    @Test
    void testGetUserProfile_UserNotFound() {
        Long userId = faker.number().randomNumber();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getUserProfile(userId));
        
        verify(userRepository).findById(userId); // Verify that findById was called
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = faker.number().randomNumber();
        UpdateUserForm form = new UpdateUserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.job().title(),
                faker.bothify("??###"),
                faker.company().industry()
        );

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(form.name());
        updatedUser.setLastname(form.lastname());
        updatedUser.setPhonenumber(form.phonenumber());
        updatedUser.setOccupation(form.occupation());
        updatedUser.setNop(form.nop());
        updatedUser.setSector(form.sector());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(userId, form);

        assertNotNull(result);
        assertEquals(form.name(), result.getName());
        assertEquals(form.lastname(), result.getLastname());
        assertEquals(form.phonenumber(), result.getPhonenumber());
        assertEquals(form.occupation(), result.getOccupation());
        assertEquals(form.nop(), result.getNop());
        assertEquals(form.sector(), result.getSector());

        verify(userRepository).findById(userId); // Verify that findById was called
        verify(userRepository).save(existingUser); // Check that save was called
    }

    @Test
    void testUpdateUser_UserNotFound() {
        Long userId = faker.number().randomNumber();
        UpdateUserForm form = new UpdateUserForm(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.job().title(),
                faker.bothify("??###"),
                faker.company().industry()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, form));
        
        verify(userRepository).findById(userId); 
        verify(userRepository, never()).save(any(User.class)); 
    }

    @Test
    void testDeactivateUser_Success() {
        Long userId = faker.number().randomNumber();
        User user = new User();
        user.setId(userId);
        user.setActive(true);  

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deactivateUser(userId);

        // Verifica se o usuário foi desativado
        assertFalse(user.getActive()); // Ajuste para a propriedade correta
        
        verify(userRepository).save(user); // Verifica que save foi chamado
    }


    @Test
    void testDeactivateUser_UserNotFound() {
        Long userId = faker.number().randomNumber();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deactivateUser(userId));
        
        verify(userRepository).findById(userId); // Verifica que findById foi chamado
        verify(userRepository, never()).save(any(User.class)); // Verifica que save não foi chamado
    }

    @Test
    void testGetUsersByRole() {
        int role = 1;
        PageRequest pageRequest = PageRequest.of(0, 10);
        User user = new User();
        user.setRole(role);

        Page<User> usersPage = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findByRole(role, pageRequest)).thenReturn(usersPage);

        UserView userView = new UserView(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getPhonenumber(),
            user.getSector(),
            user.getOccupation(),
            user.getNop(),
            user.getEmail(),
            user.getRole(),
            user.isEnabled()
        );
        Page<UserView> userViewsPage = new PageImpl<>(Collections.singletonList(userView));
        when(userViewMapper.map(user)).thenReturn(userView);

        Page<UserView> result = userService.getUsersCollaborators(role, pageRequest);


        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userView, result.getContent().get(0));
        

        verify(userRepository).findByRole(role, pageRequest); 
    }
    
    @Test
    void testGetUsersCollaborators_Success() {
        int role = 1;
        PageRequest pageRequest = PageRequest.of(0, 10);
        User user = new User();
        user.setRole(role);

        Page<User> usersPage = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findByRole(role, pageRequest)).thenReturn(usersPage);

        UserView userView = new UserView(
            user.getId(),
            user.getName(),
            user.getLastname(),
            user.getPhonenumber(),
            user.getSector(),
            user.getOccupation(),
            user.getNop(),
            user.getEmail(),
            user.getRole(),
            user.isEnabled()
        );
        Page<UserView> userViewsPage = new PageImpl<>(Collections.singletonList(userView));
        when(userViewMapper.map(user)).thenReturn(userView);

        Page<UserView> result = userService.getUsersCollaborators(role, pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userView, result.getContent().get(0));
        
        verify(userRepository).findByRole(role, pageRequest);
    }

}


