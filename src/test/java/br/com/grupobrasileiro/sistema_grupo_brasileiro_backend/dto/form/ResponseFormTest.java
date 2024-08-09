package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

public class ResponseFormTest {

    private final Faker faker = new Faker();

    @Test
    public void testValidToken() {
        String fakeToken = faker.internet().uuid();
        ResponseForm form = new ResponseForm(fakeToken);
        assertEquals(fakeToken, form.token());
    }

    @Test
    public void testNullToken() {
        ResponseForm form = new ResponseForm(null);
        assertNull(form.token());
    }

    @Test
    public void testEmptyToken() {
        ResponseForm form = new ResponseForm("");
        assertEquals("", form.token());
    }

    @Test
    public void testTokenWithSpaces() {
        String tokenWithSpaces = "  token  with  spaces  ";
        ResponseForm form = new ResponseForm(tokenWithSpaces);
        assertEquals(tokenWithSpaces, form.token());
    }
}
