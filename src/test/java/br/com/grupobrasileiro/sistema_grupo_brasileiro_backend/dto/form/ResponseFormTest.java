package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseFormTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void testResponseFormCreation() {
        
        String token = faker.lorem().characters(20);

        
        ResponseForm responseForm = new ResponseForm(token);

        
        assertEquals(token, responseForm.token());
    }
}
