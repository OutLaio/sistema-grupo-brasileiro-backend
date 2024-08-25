package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;
import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserProfileViewMapperTest {

    private UserProfileViewMapper userProfileViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        userProfileViewMapper = new UserProfileViewMapper();
        faker = new Faker();
    }

    @Test
    void shouldMapUserToUserProfileView() {
        // Arrange
        User user = new User();
        user.setId(faker.number().randomNumber());
        user.setName(faker.name().fullName());
        user.setLastname(faker.name().lastName());
        user.setPhonenumber(faker.phoneNumber().phoneNumber());
        user.setSector(faker.lorem().word());
        user.setOccupation(faker.lorem().word());
        user.setNop(faker.number().digits(5));
        user.setEmail(faker.internet().emailAddress());

        // Act
        UserProfileView userProfileView = userProfileViewMapper.map(user);

        // Assert
        assertNotNull(userProfileView, "UserProfileView should not be null");
        assertEquals(user.getId(), userProfileView.id(), "User ID should match");
        assertEquals(user.getName(), userProfileView.name(), "User Name should match");
        assertEquals(user.getLastname(), userProfileView.lastname(), "User Lastname should match");
        assertEquals(user.getPhonenumber(), userProfileView.phonenumber(), "User Phonenumber should match");
        assertEquals(user.getSector(), userProfileView.sector(), "User Sector should match");
        assertEquals(user.getOccupation(), userProfileView.occupation(), "User Occupation should match");
        assertEquals(user.getNop(), userProfileView.nop(), "User Nop should match");
        assertEquals(user.getEmail(), userProfileView.email(), "User Email should match");
    }
}

