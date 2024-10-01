package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import jakarta.transaction.Transactional;


import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class BPrintedRepositoryTest {

    @Autowired
    private BPrintedRepository bPrintedRepository;

    @Autowired
    private BriefingRepository briefingRepository; 
    
    @Autowired
    private PrintedTypeRepository printedTypeRepository; 

    @Autowired
    private ProjectRepository projectRepository; 
    
    @Autowired
    private EmployeeRepository employeeRepository;

    private Briefing briefing; 
    private PrintedType printedType;
    private Project project; 

    // Setup para os testes
    @BeforeEach
    void setUp() {
        // Limpar os repositórios antes de cada teste
        bPrintedRepository.deleteAll();
        briefingRepository.deleteAll();
        printedTypeRepository.deleteAll();
        projectRepository.deleteAll(); 

        // Criar e persistir um tipo de impressão
        printedType = new PrintedType();
        printedType.setDescription("Tipo de Impressão 1");
        printedType = printedTypeRepository.save(printedType);

        // Criar e persistir um colaborador (Employee)
        Employee employee = new Employee(); // Supondo que Employee tem um construtor padrão
        employee.setName("Colaborador Teste"); // Defina os atributos necessários
        employee = employeeRepository.save(employee); // Salve o colaborador

        // Criar e persistir um projeto
        project = new Project();
        project.setTitle("Projeto 1");
        project.setDisabled(false);
        project.setClient(employee); // Atribuindo o colaborador como cliente
        project = projectRepository.save(project);

        // Criar e persistir um briefing
        briefing = new Briefing();
        briefing.setProject(project);
        // briefing.setBriefingType(); // Essa linha continua comentada
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição do briefing");
        briefing = briefingRepository.save(briefing);
    }

    @Test
    @DisplayName("Should save a BPrinted correctly")
    @Rollback
    void testSaveBPrinted() {
        // Arrange
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing); // Associar ao briefing criado no setUp
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        
        // Act
        BPrinted savedPrinted = bPrintedRepository.save(bPrinted);

        // Assert
        assertThat(savedPrinted).isNotNull();
        assertThat(savedPrinted.getId()).isNotNull();
        assertThat(savedPrinted.getPaperType()).isEqualTo(bPrinted.getPaperType());
        assertThat(savedPrinted.getFolds()).isEqualTo(bPrinted.getFolds());
        assertThat(savedPrinted.getPages()).isEqualTo(bPrinted.getPages());
    }

    @Test
    @DisplayName("Should find a BPrinted by ID")
    @Rollback
    void testFindBPrintedById() {
        // Arrange
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing); // Associar ao briefing
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        BPrinted savedPrinted = bPrintedRepository.save(bPrinted);

        // Act
        Optional<BPrinted> foundPrinted = bPrintedRepository.findById(savedPrinted.getId());

        // Assert
        assertThat(foundPrinted).isPresent();
        assertThat(foundPrinted.get().getId()).isEqualTo(savedPrinted.getId());
    }

    @Test
    @DisplayName("Should update a BPrinted")
    @Rollback
    void testUpdateBPrinted() {
        // Arrange
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing); // Associar ao briefing
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        BPrinted savedPrinted = bPrintedRepository.save(bPrinted);

        // Act - Atualiza o tipo de papel
        savedPrinted.setPaperType("Papel A3");
        BPrinted updatedPrinted = bPrintedRepository.save(savedPrinted);

        // Assert
        assertThat(updatedPrinted.getPaperType()).isEqualTo("Papel A3");
    }

    @Test
    @DisplayName("Should delete a BPrinted")
    @Rollback
    void testDeleteBPrinted() {
        // Arrange
        BPrinted bPrinted = new BPrinted();
        bPrinted.setBriefing(briefing); // Associar ao briefing
        bPrinted.setPaperType("Papel A4");
        bPrinted.setFolds(2);
        bPrinted.setPages(50);
        BPrinted savedPrinted = bPrintedRepository.save(bPrinted);

        // Act
        bPrintedRepository.delete(savedPrinted);
        Optional<BPrinted> foundPrinted = bPrintedRepository.findById(savedPrinted.getId());

        // Assert
        assertThat(foundPrinted).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all BPrinted")
    void testFindAllBPrinted() {
        // Arrange
        BPrinted printed1 = new BPrinted();
        printed1.setBriefing(briefing); // Associar ao briefing
        printed1.setPaperType("Papel A4");
        BPrinted printed2 = new BPrinted();
        printed2.setBriefing(briefing); // Associar ao briefing
        printed2.setPaperType("Papel A3");
        bPrintedRepository.save(printed1);
        bPrintedRepository.save(printed2);

        // Act
        Iterable<BPrinted> allPrinted = bPrintedRepository.findAll();

        // Assert
        assertThat(allPrinted).hasSize(2);
    }
}
