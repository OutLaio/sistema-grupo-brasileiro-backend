package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import jakarta.persistence.EntityManager;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class VersionRepositoryTest {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private EntityManager entityManager;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

   

    @Test
    @DisplayName("Should return 0 when no versions are associated with briefing")
    void testCountVersionsByNonExistingBriefingId() {
        long count = versionRepository.countVersionsByBriefingId(999L);
        assertThat(count).isEqualTo(0);
    }

  
 

    private Briefing createTestBriefing() {
        // Criar um projeto com um cliente
        Project project = createOrFetchProject();

        // Criar um tipo de briefing
        BriefingType briefingType = createOrFetchBriefingType();

        // Criar o briefing
        Briefing briefing = new Briefing();
        briefing.setProject(project); // Associando o projeto
        briefing.setBriefingType(briefingType); // Associando o tipo de briefing
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(5));
        briefing.setDetailedDescription(faker.lorem().sentence());

        return briefingRepository.save(briefing);
    }

    private BriefingType createOrFetchBriefingType() {
        // Cria um novo tipo de briefing se não existir
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de Briefing Teste");
        return briefingTypeRepository.save(briefingType);
    }

    private Project createOrFetchProject() {
        // Cria um novo cliente fictício e salva
        Employee client = createTestClient();

        // Cria o projeto e associa o cliente
        Project project = new Project();
        project.setTitle("Projeto Teste");
        project.setDisabled(false); // Define como ativo
        project.setClient(client); // Associando um cliente
        return projectRepository.save(project); // Salva o projeto
    }

    private Employee createTestClient() {
        // Cria um novo cliente fictício
        Employee client = new Employee();
        client.setName("Cliente Teste");
        // Adicione outros campos obrigatórios conforme necessário
        return client; // Retorna o cliente para ser salvo
    }


       private Version saveTestVersion(Briefing briefing, int versionNumber, String feedback, boolean clientApprove, boolean supervisorApprove) {
        Version version = new Version();
        version.setBriefing(briefing);
        version.setNumVersion(versionNumber);
        version.setProductLink(faker.internet().url());
        version.setClientApprove(clientApprove);
        version.setSupervisorApprove(supervisorApprove);
        version.setFeedback(feedback);
        return versionRepository.save(version);
    }
}

