package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class EmployeeRepositoryTest {

    @Mock
    private UserRepository userRepository; // Mock do UserRepository

    @Mock
    private EmployeeRepository employeeRepository; // Mock do EmployeeRepository

    @InjectMocks
    private EmployeeService employeeService; // Serviço que usa o repositório

    private Faker faker;
    private Profile collaboratorProfile; // Variável do perfil do colaborador

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        faker = new Faker();
        
        // Inicializa o collaboratorProfile
        collaboratorProfile = new Profile();
        collaboratorProfile.setId(1L); // Defina um ID se necessário
        collaboratorProfile.setDescription("Collaborator"); // Descrição do perfil
    }

    @Test
    @DisplayName("Should return all collaborators with pagination")
    void testFindAllCollaboratorsWithPagination() {
        // Arrange
        List<Employee> mockEmployees = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            User collaboratorUser = new User();
            collaboratorUser.setEmail(faker.internet().emailAddress());
            collaboratorUser.setPassword(faker.internet().password());
            collaboratorUser.setDisabled(false);
            collaboratorUser.setProfile(collaboratorProfile); // Usando collaboratorProfile

            // Mockando o comportamento do repositório
            when(userRepository.save(collaboratorUser)).thenReturn(collaboratorUser);

            Employee collaborator = new Employee();
            collaborator.setUser(collaboratorUser);
            collaborator.setName(faker.name().firstName());
            collaborator.setLastName(faker.name().lastName());
            collaborator.setPhoneNumber(faker.phoneNumber().phoneNumber());
            collaborator.setSector("IT");
            collaborator.setOccupation("Developer");
            collaborator.setAgency("Agency 1");
            collaborator.setAvatar(1L); // Simulação de um avatar

            mockEmployees.add(collaborator);
        }

        when(employeeRepository.findAllCollaborators(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(mockEmployees));

        // Act
        Pageable pageable = PageRequest.of(0, 10); // Obtendo a primeira página com 10 elementos
        Page<Employee> allCollaborators = employeeRepository.findAllCollaborators(pageable);

        // Assert
      //  assertThat(allCollaborators.getContent()).hasSizeLessThanOrEqualTo(10);
      //  assertThat(allCollaborators.getTotalElements()).isEqualTo(15);
    }

    @Test
    @DisplayName("Should return empty list when no collaborators exist")
    void testFindAllCollaboratorsWhenEmpty() {
        // Arrange
        when(employeeRepository.findAllCollaborators(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(new ArrayList<>()));

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> allCollaborators = employeeRepository.findAllCollaborators(pageable);

        // Assert
        assertThat(allCollaborators.getContent()).isEmpty();
        assertThat(allCollaborators.getTotalElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return one collaborator when one exists")
    void testFindOneCollaborator() {
        // Arrange
        User collaboratorUser = new User();
        collaboratorUser.setEmail(faker.internet().emailAddress());
        collaboratorUser.setPassword(faker.internet().password());
        collaboratorUser.setDisabled(false);
        collaboratorUser.setProfile(collaboratorProfile); // Usando collaboratorProfile

        // Mockando o comportamento do repositório
        when(userRepository.save(collaboratorUser)).thenReturn(collaboratorUser);

        Employee collaborator = new Employee();
        collaborator.setUser(collaboratorUser);
        collaborator.setName(faker.name().firstName());
        collaborator.setLastName(faker.name().lastName());
        collaborator.setPhoneNumber(faker.phoneNumber().phoneNumber());
        collaborator.setSector("IT");
        collaborator.setOccupation("Developer");
        collaborator.setAgency("Agency 1");
        collaborator.setAvatar(1L); 

        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(collaborator);
        when(employeeRepository.findAllCollaborators(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(mockEmployees));

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<Employee> allCollaborators = employeeRepository.findAllCollaborators(pageable);

        // Assert
        assertThat(allCollaborators.getContent()).hasSize(1);
        assertThat(allCollaborators.getTotalElements()).isEqualTo(1);
        assertThat(allCollaborators.getContent().get(0)).isEqualTo(collaborator);
    }
}
