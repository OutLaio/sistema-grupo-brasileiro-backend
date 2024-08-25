package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testSaveAndFindByEmail() {
        // Generate fake data
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.number().digits(4);
        String password = faker.internet().password();
        Integer roleCode = RoleEnum.ROLE_CLIENT.getCode();

        // Create and save a user
        User user = new User(null, name, lastname, phonenumber, sector, occupation, nop, email, password, roleCode);
        userRepository.save(user);

        // Verify the user is saved and can be retrieved
        UserDetails userDetails = userRepository.findByEmail(email);
        assertNotNull(userDetails, "User should not be null.");
        assertEquals(email, userDetails.getUsername(), "Email of the found user does not match.");
    }

    @Test
    public void testFindAllUsers() {
        // Generate fake data
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.number().digits(4);
        String password = faker.internet().password();
        Integer roleCode = RoleEnum.ROLE_CLIENT.getCode();

        // Create and save a user with CLIENT role
        User user = new User(null, name, lastname, phonenumber, sector, occupation, nop, email, password, roleCode);
        userRepository.save(user);

        // Retrieve all users
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty(), "User list should not be empty.");

        // Verify that the user is in the list
        boolean userFound = users.stream().anyMatch(u -> u.getEmail().equals(email));
        assertTrue(userFound, "User with email was not found in the list.");
    }

    @Test
    public void testFindByName() {
        // Generate fake data
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.number().digits(4);
        String password = faker.internet().password();
        Integer roleCode = RoleEnum.ROLE_CLIENT.getCode();

        // Create and save a user
        User user = new User(null, name, lastname, phonenumber, sector, occupation, nop, email, password, roleCode);
        userRepository.save(user);

        // Verify the user is saved in the database
        User savedUser = userRepository.findByName(name);
        assertNotNull(savedUser, "User should not be null.");

        // Ensure the retrieved user matches the saved user
        assertEquals(email, savedUser.getEmail(), "Email of the found user does not match.");
        assertEquals(name, savedUser.getName(), "Name of the found user does not match.");
        assertEquals(lastname, savedUser.getLastname(), "Lastname of the found user does not match.");
        assertEquals(phonenumber, savedUser.getPhonenumber(), "Phonenumber of the found user does not match.");
        assertEquals(sector, savedUser.getSector(), "Sector of the found user does not match.");
        assertEquals(occupation, savedUser.getOccupation(), "Occupation of the found user does not match.");
        assertEquals(nop, savedUser.getNop(), "Nop of the found user does not match.");
        assertEquals(password, savedUser.getPassword(), "Password of the found user does not match.");
        assertEquals(roleCode, savedUser.getRole(), "Role of the found user does not match.");
    }
}
