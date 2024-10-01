package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
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

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MeasurementRepositoryTest {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private Measurement measurement;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Criação do Profile
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        profile = profileRepository.save(profile); // Salvar o perfil no banco

        // Exemplo de criação do User
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setDisabled(false);
        user.setProfile(profile); // Associar o perfil ao usuário
        user = userRepository.save(user); // Salvar o usuário no banco

        // Criação do Employee
        Employee employee = new Employee();
        employee.setName("Cliente Teste");
        employee.setLastName("Sobrenome Teste");
        employee.setPhoneNumber("123456789");
        employee.setSector("Vendas");
        employee.setOccupation("Gerente");
        employee.setAgency("Agência Central");
        employee.setAvatar(1L); // Exemplo de valor
        employee.setUser(user); // Associando o usuário
        employee = employeeRepository.save(employee); // Salvar o empregado

        // Criação de um BriefingType
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de briefing de teste");
        briefingType = briefingTypeRepository.save(briefingType);

        // Criação do Project
        Project project = new Project();
        project.setTitle("Título do Projeto de Teste");
        project.setStatus("Ativo");
        project.setDisabled(false);
        project.setClient(employee); // Usar o Employee salvo como cliente
        project.setCollaborator(employee); // Use o mesmo Employee como colaborador para simplificação
        project = projectRepository.save(project); // Salvar o projeto

        // Criação do Briefing
        briefing = new Briefing();
        briefing.setBriefingType(briefingType);
        briefing.setProject(project); // Associando o projeto ao briefing
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing para teste");
        briefing = briefingRepository.save(briefing); // Salvar o Briefing

        // Criação da Measurement
        measurement = new Measurement();
        measurement.setBriefing(briefing);
        measurement.setHeight(BigDecimal.valueOf(1.75));
        measurement.setLength(BigDecimal.valueOf(2.50));
    }

    @Test
    @DisplayName("Deve salvar e carregar uma Measurement")
    void shouldSaveAndLoadMeasurement() {
        // Salvar a Measurement
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertThat(savedMeasurement.getId()).isNotNull();

        // Carregar a Measurement do banco
        Measurement foundMeasurement = measurementRepository.findById(savedMeasurement.getId()).orElse(null);
        assertThat(foundMeasurement).isNotNull();
        assertThat(foundMeasurement.getHeight()).isEqualTo(measurement.getHeight());
        assertThat(foundMeasurement.getLength()).isEqualTo(measurement.getLength());
        assertThat(foundMeasurement.getBriefing()).isEqualTo(briefing);
    }

    @Test
    @DisplayName("Deve excluir uma Measurement")
    void shouldDeleteMeasurement() {
        // Salvar a Measurement
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertThat(savedMeasurement.getId()).isNotNull();

        // Excluir a Measurement
        measurementRepository.delete(savedMeasurement);

        // Verificar se a Measurement foi excluída
        Measurement foundMeasurement = measurementRepository.findById(savedMeasurement.getId()).orElse(null);
        assertThat(foundMeasurement).isNull();
    }
}
