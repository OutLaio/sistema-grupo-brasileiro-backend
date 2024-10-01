package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project; // Certifique-se de que esta classe está presente
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BriefingRepositoryTest {

    @Autowired
    private BriefingRepository briefingRepository;
    
    @Autowired
    private ProjectRepository projectRepository; 

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository; 

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    

    /**
     * Testa a recuperação de um briefing que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing briefing")
    void testRetrieveNonExistingBriefing() {
        // Act
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedBriefing).isNotPresent();
    }

   

    /**
     * Testa a criação de um briefing com dados inválidos.
     */
    @Test
    @DisplayName("Should throw exception when creating briefing with null fields")
    void testCreateBriefingWithNullFields() {
        // Arrange
        Briefing briefing = new Briefing();
        briefing.setProject(null); // Define projeto como null
        briefing.setBriefingType(null); // Define tipo de briefing como null
        briefing.setStartTime(null); // Define hora de início como null
        briefing.setExpectedTime(null); // Define hora esperada como null
        briefing.setDetailedDescription(null); // Define descrição como null

        // Act & Assert
        assertThrows(Exception.class, () -> {
            briefingRepository.save(briefing); // Tenta salvar o briefing
        });
    }

    private Briefing createTestBriefing() {
        // Criação do cliente
        Employee client = createTestEmployee(); // Cria e salva um funcionário

        // Criação e configuração do projeto
        Project project = new Project();
        project.setTitle("Test Project");
        project.setStatus("In Progress");
        project.setDisabled(false);
        project.setClient(client); // Defina o cliente que não pode ser nulo

        project = projectRepository.save(project); // Salve o projeto no banco de dados

        // Criação e configuração do tipo de briefing
        BriefingType briefingType = new BriefingType();
        briefingType.setId(1L); // Verifique se o tipo com esse ID já existe no banco

        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(5));
        briefing.setDetailedDescription(faker.lorem().paragraph());
        briefing.setOtherCompany(faker.company().name());

        return briefing; // Retorna o briefing sem salvar
    }

    
    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setId(1L); // Defina um ID fixo ou use um gerador de ID se apropriado

        // Salve o empregado no banco de dados
        return employeeRepository.save(employee);
    }

}