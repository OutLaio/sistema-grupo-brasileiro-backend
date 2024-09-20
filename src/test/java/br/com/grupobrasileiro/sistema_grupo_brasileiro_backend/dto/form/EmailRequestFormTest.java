//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import com.github.javafaker.Faker;
//
//public class EmailRequestFormTest {
//
//    private final Faker faker = new Faker();
//
//    @Test
//    public void testValidEmail() {
//        String fakeEmail = faker.internet().emailAddress();
//        EmailRequestForm form = new EmailRequestForm(fakeEmail);
//        assertEquals(fakeEmail, form.email());
//    }
//
//    @Test
//    public void testNullEmail() {
//        EmailRequestForm form = new EmailRequestForm(null);
//        assertNull(form.email());
//    }
//
//    @Test
//    public void testEmptyEmail() {
//        EmailRequestForm form = new EmailRequestForm("");
//        assertEquals("", form.email());
//    }
//
//    @Test
//    public void testEmailWithSpecialCharacters() {
//        String specialEmail = "user+label@example.com";
//        EmailRequestForm form = new EmailRequestForm(specialEmail);
//        assertEquals(specialEmail, form.email());
//    }
//
//
//}
