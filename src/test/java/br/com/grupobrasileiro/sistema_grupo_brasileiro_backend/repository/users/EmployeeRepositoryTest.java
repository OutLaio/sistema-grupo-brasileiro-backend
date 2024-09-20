package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import java.util.List;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa o método findAllCollaborators do EmployeeRepository.
     * Verifica se ele retorna apenas os funcionários com o perfil de ID 2.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should return only collaborators (Profile ID = 2)")
    void testFindAllCollaborators() {
        // Arrange
        // Criação de perfis
        Profile collaboratorProfile = new Profile();
        collaboratorProfile.setDescription("Collaborator");
        collaboratorProfile.setId(2L); // ID 2 para os colaboradores

        Profile otherProfile = new Profile();
        otherProfile.setDescription("Manager");
        otherProfile.setId(1L); // Outro perfil

        // Criação de usuários
        User collaboratorUser = new User();
        collaboratorUser.setEmail(faker.internet().emailAddress());
        collaboratorUser.setPassword(faker.internet().password());
        collaboratorUser.setDisabled(false);
        collaboratorUser.setProfile(collaboratorProfile);

        User otherUser = new User();
        otherUser.setEmail(faker.internet().emailAddress());
        otherUser.setPassword(faker.internet().password());
        otherUser.setDisabled(false);
        otherUser.setProfile(otherProfile);

        // Criação de funcionários
        Employee collaborator = new Employee();
        collaborator.setUser(collaboratorUser);
        collaborator.setName(faker.name().firstName());
        collaborator.setLastName(faker.name().lastName()); 

        Employee manager = new Employee();
        manager.setUser(otherUser);
        manager.setName(faker.name().firstName()); 
        manager.setLastName(faker.name().lastName()); 

        // Salvando os dados no banco
        employeeRepository.save(collaborator);
        employeeRepository.save(manager);

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Employee> collaboratorsPage = employeeRepository.findAllCollaborators(pageable);

        // Assert
        List<Employee> collaborators = collaboratorsPage.getContent();
        assertThat(collaborators).isNotEmpty();
        assertThat(collaborators).allMatch(emp -> emp.getUser().getProfile().getId() == 2L);
        assertThat(collaborators).noneMatch(emp -> emp.getUser().getProfile().getId() == 1L);
    }
}
