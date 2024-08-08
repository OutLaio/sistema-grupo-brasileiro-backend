package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserViewTest {

    private Faker faker = new Faker();

    @Test
    void testUserViewCreation() {
        UserView userView = new UserView(
                1L,
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().phoneNumber(),
                faker.company().industry(),
                faker.job().title(),
                faker.idNumber().valid(),
                faker.internet().emailAddress(),
                faker.number().numberBetween(1, 3),
                faker.bool().bool() 
        );

        assertEquals(1L, userView.id());
        assertEquals("João", userView.name()); 
        assertEquals("Silva", userView.lastname()); 
        assertEquals("21999999999", userView.phonenumber()); 
        assertEquals("Tecnologia", userView.sector()); 
        assertEquals("Desenvolvedor", userView.occupation()); 
        assertEquals("123", userView.nop()); 
        assertEquals("joao.silva@example.com", userView.email());
        assertEquals(RoleEnum.ROLE_CLIENT.getCode(), userView.role());
        assertEquals(true, userView.status()); 
    }
}
