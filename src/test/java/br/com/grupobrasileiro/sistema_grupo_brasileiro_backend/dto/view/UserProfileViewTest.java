//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;
//
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserProfileViewTest {
//
//    private final Faker faker = new Faker();
//
//    @Test
//    public void testUserProfileView() {
//        Long id = faker.number().randomNumber();
//        String name = faker.name().firstName();
//        String lastname = faker.name().lastName();
//        String phonenumber = faker.phoneNumber().phoneNumber();
//        String sector = faker.company().industry();
//        String occupation = faker.job().title();
//        String nop = faker.number().digits(10); // Example number
//        String email = faker.internet().emailAddress();
//
//        // Create an instance of UserProfileView
//        UserProfileView userProfileView = new UserProfileView(
//            id,
//            name,
//            lastname,
//            phonenumber,
//            sector,
//            occupation,
//            nop,
//            email
//        );
//
//        // Check if data is not null
//        assertNotNull(userProfileView.id());
//        assertNotNull(userProfileView.name());
//        assertNotNull(userProfileView.lastname());
//        assertNotNull(userProfileView.phonenumber());
//        assertNotNull(userProfileView.sector());
//        assertNotNull(userProfileView.occupation());
//        assertNotNull(userProfileView.nop());
//        assertNotNull(userProfileView.email());
//
//        // Check if the generated data is as expected
//        assertEquals(id, userProfileView.id());
//        assertEquals(name, userProfileView.name());
//        assertEquals(lastname, userProfileView.lastname());
//        assertEquals(phonenumber, userProfileView.phonenumber());
//        assertEquals(sector, userProfileView.sector());
//        assertEquals(occupation, userProfileView.occupation());
//        assertEquals(nop, userProfileView.nop());
//        assertEquals(email, userProfileView.email());
//
//        // Additional validation
//        // Check if email follows a basic pattern
//        assertTrue(email.contains("@"), "Email should contain '@'");
//        assertTrue(email.contains("."), "Email should contain '.'");
//
//        // Check if phone number meets a basic length requirement (for example, 10 digits)
//        assertTrue(phonenumber.length() >= 10, "Phone number should be at least 10 digits long");
//
//        // Additional edge case test for numeric ID
//        assertTrue(userProfileView.id() > 0, "ID should be a positive number");
//
//        // Additional tests for field lengths and formats if applicable
//        assertTrue(userProfileView.name().length() <= 50, "Name should not exceed 50 characters");
//        assertTrue(userProfileView.email().length() <= 100, "Email should not exceed 100 characters");
//    }
//}
