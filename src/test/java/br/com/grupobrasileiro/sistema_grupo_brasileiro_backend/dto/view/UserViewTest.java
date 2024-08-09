package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserViewTest {

    private final Faker faker = new Faker();

    @Test
    public void testUserView() {
        
        Long id = faker.number().randomNumber();
        String name = faker.name().firstName();
        String lastname = faker.name().lastName();
        String phonenumber = faker.phoneNumber().phoneNumber();
        String sector = faker.company().industry();
        String occupation = faker.job().title();
        String nop = faker.number().digits(10); // Example number
        String email = faker.internet().emailAddress();
        Integer role = faker.number().numberBetween(1, 5); // Example role between 1 and 5
        Boolean status = faker.bool().bool(); // Random boolean value for status

        // Create an instance of UserView
        UserView userView = new UserView(
            id,
            name,
            lastname,
            phonenumber,
            sector,
            occupation,
            nop,
            email,
            role,
            status
        );

        // Check that the fields are not null
        assertNotNull(userView.id());
        assertNotNull(userView.name());
        assertNotNull(userView.lastname());
        assertNotNull(userView.phonenumber());
        assertNotNull(userView.sector());
        assertNotNull(userView.occupation());
        assertNotNull(userView.nop());
        assertNotNull(userView.email());
        assertNotNull(userView.role());
        assertNotNull(userView.status());

        // Verify that the data matches the expected values
        assertEquals(id, userView.id());
        assertEquals(name, userView.name());
        assertEquals(lastname, userView.lastname());
        assertEquals(phonenumber, userView.phonenumber());
        assertEquals(sector, userView.sector());
        assertEquals(occupation, userView.occupation());
        assertEquals(nop, userView.nop());
        assertEquals(email, userView.email());
        assertEquals(role, userView.role());
        assertEquals(status, userView.status());
    }
}
