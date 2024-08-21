package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

    @DataJpaTest
    public class UserRepositoryTest {

        @Autowired
        private UserRepository userRepository;

        private Faker faker = new Faker();

        @Test
        public void testFindByRole() {
            // Gerar dados fictícios com Java Faker
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

            // Cria e salva usuários de teste com a role CLIENT
            User user1 = new User(null, name, lastname, phonenumber, sector, occupation, nop, email1, password, roleCode);
            User user2 = new User(null, name, lastname, phonenumber, sector, occupation, nop, email2, password, roleCode);
            userRepository.save(user1);
            userRepository.save(user2);

            // Verificar se os usuários foram realmente salvos
            UserDetails userDetails1 = userRepository.findByEmail(email1);
            UserDetails userDetails2 = userRepository.findByEmail(email2);
            assertNotNull(userDetails1, "Usuário com email1 não foi salvo.");
            assertNotNull(userDetails2, "Usuário com email2 não foi salvo.");

            // Convertendo UserDetails para User
            User foundUser1 = (User) userDetails1;
            User foundUser2 = (User) userDetails2;

            // Verifica se o email do usuário encontrado corresponde ao email esperado
            assertEquals(email1, foundUser1.getUsername(), "Email do usuário encontrado não corresponde ao email esperado.");
            assertEquals(email2, foundUser2.getUsername(), "Email do usuário encontrado não corresponde ao email esperado.");

            // Recupera a página de usuários com a role CLIENT
            Page<User> usersPage = userRepository.findByRole(roleCode, PageRequest.of(0, 10));

            // Verifica se a página de usuários não está vazia e contém os usuários esperados
            assertNotNull(usersPage, "A página de usuários é nula.");
            assertTrue(usersPage.getTotalElements() > 0, "A página de usuários está vazia.");

            // Verifica se os usuários esperados estão na lista
            boolean user1Found = usersPage.getContent().stream().anyMatch(u -> u.getEmail().equals(email1));
            boolean user2Found = usersPage.getContent().stream().anyMatch(u -> u.getEmail().equals(email2));

            assertTrue(user1Found, "Usuário com email1 não encontrado.");
            assertTrue(user2Found, "Usuário com email2 não encontrado.");
        }
    }
