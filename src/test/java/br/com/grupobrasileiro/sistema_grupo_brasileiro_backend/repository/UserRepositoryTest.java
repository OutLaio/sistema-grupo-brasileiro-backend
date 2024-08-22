package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@RunWith(SpringRunner.class)
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
        
        // Ensure the retrieved user details are from the correct user
        assertEquals(email, userDetails.getUsername(), "Email of the found user does not match.");
    }

    @Test
    public void testFindByRole() {
        // Generate fake data
        String email1 = faker.internet().emailAddress();
        String email2 = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.number().digits(4);
        String password = faker.internet().password();
        Integer roleCode = RoleEnum.ROLE_CLIENT.getCode();

        // Create and save test users with CLIENT role
        User user1 = new User(null, name, lastname, phonenumber, sector, occupation, nop, email1, password, roleCode);
        User user2 = new User(null, name, lastname, phonenumber, sector, occupation, nop, email2, password, roleCode);
        userRepository.save(user1);
        userRepository.save(user2);

        // Retrieve the page of users with CLIENT role
        Page<User> usersPage = userRepository.findByRole(roleCode, PageRequest.of(0, 10));

        // Verify that the page is not empty and contains expected users
        assertNotNull(usersPage, "The user page is null.");
        assertTrue(usersPage.getTotalElements() > 0, "The user page is empty.");

        // Verify that expected users are in the list
        boolean user1Found = usersPage.getContent().stream().anyMatch(u -> u.getEmail().equals(email1));
        boolean user2Found = usersPage.getContent().stream().anyMatch(u -> u.getEmail().equals(email2));

        assertTrue(user1Found, "User with email1 was not found in the page.");
        assertTrue(user2Found, "User with email2 was not found in the page.");
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
        
        // Ensure the retrieved user is the same as the one saved
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