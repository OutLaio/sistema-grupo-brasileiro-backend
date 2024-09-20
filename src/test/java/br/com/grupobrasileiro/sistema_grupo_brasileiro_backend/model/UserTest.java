//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;
//
//import com.github.javafaker.Faker;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserTest {
//
//    private Faker faker;
//
//    @BeforeEach
//    void setUp() {
//        faker = new Faker();
//    }
//
//    @Test
//    public void testUserGettersAndSetters() {
//        User user = new User();
//
//        // Generate dummy data using Faker
//        Long id = faker.number().randomNumber();
//        String name = faker.name().firstName();
//        String lastname = faker.name().lastName();
//        String phonenumber = faker.phoneNumber().phoneNumber();
//        String sector = faker.company().industry();
//        String occupation = faker.job().title();
//        String nop = faker.number().digits(6);
//        String email = faker.internet().emailAddress();
//        Integer role = RoleEnum.ROLE_CLIENT.getCode(); // Update as needed
//
//        // Configure user with dummy data
//        user.setId(id);
//        user.setName(name);
//        user.setLastname(lastname);
//        user.setPhonenumber(phonenumber);
//        user.setSector(sector);
//        user.setOccupation(occupation);
//        user.setNop(nop);
//        user.setEmail(email);
//        user.setRole(role);
//
//        // Verify that getters return expected values
//        assertEquals(id, user.getId());
//        assertEquals(name, user.getName());
//        assertEquals(lastname, user.getLastname());
//        assertEquals(phonenumber, user.getPhonenumber());
//        assertEquals(sector, user.getSector());
//        assertEquals(occupation, user.getOccupation());
//        assertEquals(nop, user.getNop());
//        assertEquals(email, user.getEmail());
//        assertEquals(role, user.getRole());
//    }
//
//    @Test
//    public void testUserWithNullFields() {
//        User user = new User();
//
//        // Create a user with null fields
//        user.setId(null);
//        user.setName(null);
//        user.setLastname(null);
//        user.setPhonenumber(null);
//        user.setSector(null);
//        user.setOccupation(null);
//        user.setNop(null);
//        user.setEmail(null);
//        user.setRole(null);
//
//        // Check if getters return null
//        assertNull(user.getId());
//        assertNull(user.getName());
//        assertNull(user.getLastname());
//        assertNull(user.getPhonenumber());
//        assertNull(user.getSector());
//        assertNull(user.getOccupation());
//        assertNull(user.getNop());
//        assertNull(user.getEmail());
//        assertNull(user.getRole());
//    }
//}
