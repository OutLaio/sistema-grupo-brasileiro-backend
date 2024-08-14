package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;


import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserViewMapperTest {

    private UserViewMapper mapper;
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        mapper = new UserViewMapper();
    }

    @Test
    void testMap() {
        // Generate fake data using Faker
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.number().digits(10);
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(); // Fake password
        Integer role = faker.number().numberBetween(1, 5); // Example role between 1 and 5
        Boolean status = faker.bool().bool(); // Random boolean value for active status

        // Create a User object with fake data
        User user = new User(
			id,
            name,
            lastname,
            phonenumber,
            sector,
            occupation,
            nop,
            email,
            password,
			role
        );
        
        user.setActive(status); // Set the active status

        // Map User to UserView
        UserView userView = mapper.map(user);

        // Verify that the fields are mapped correctly
        // UserView does not have an ID set directly, so it may be null
        assertNull(userView.id(), "ID should be null."); // If your UserView does not set ID, this is expected

        // Verify other fields
        assertEquals(name, userView.name(), "Name should match.");
        assertEquals(lastname, userView.lastname(), "Lastname should match.");
        assertEquals(phonenumber, userView.phonenumber(), "Phonenumber should match.");
        assertEquals(sector, userView.sector(), "Sector should match.");
        assertEquals(occupation, userView.occupation(), "Occupation should match.");
        assertEquals(nop, userView.nop(), "NOP should match.");
        assertEquals(email, userView.email(), "Email should match.");
        assertEquals(role, userView.role(), "Role should match.");
        assertEquals(status, userView.status(), "Status should match.");
    }
}

