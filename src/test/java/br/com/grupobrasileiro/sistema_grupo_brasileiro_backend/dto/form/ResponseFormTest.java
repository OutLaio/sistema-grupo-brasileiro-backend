package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.TokenView;

public class ResponseFormTest {

    private final Faker faker = new Faker();

    @Test
    public void testValidToken() {
        String fakeToken = faker.internet().uuid();
        TokenView form = new TokenView(fakeToken);
        assertEquals(fakeToken, form.token());
    }

    @Test
    public void testNullToken() {
        TokenView form = new TokenView(null);
        assertNull(form.token());
    }

    @Test
    public void testEmptyToken() {
        TokenView form = new TokenView("");
        assertEquals("", form.token());
    }

    @Test
    public void testTokenWithSpaces() {
        String tokenWithSpaces = "  token  with  spaces  ";
        TokenView form = new TokenView(tokenWithSpaces);
        assertEquals(tokenWithSpaces, form.token());
    }
}
