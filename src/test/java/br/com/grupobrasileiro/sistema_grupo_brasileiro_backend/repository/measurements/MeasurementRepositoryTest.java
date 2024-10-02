package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.Random.class)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        profile = profileRepository.save(profile);

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setDisabled(false);
        user.setProfile(profile);
        user = userRepository.save(user);

        Employee employee = new Employee();
        employee.setName("Cliente Teste");
        employee.setLastName("Sobrenome Teste");
        employee.setPhoneNumber("123456789");
        employee.setSector("Vendas");
        employee.setOccupation("Gerente");
        employee.setAgency("Agência Central");
        employee.setAvatar(1L);
        employee.setUser(user);
        employee = employeeRepository.save(employee);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de briefing de teste");
        briefingType = briefingTypeRepository.save(briefingType);

        Project project = new Project();
        project.setTitle("Título do Projeto de Teste");
        project.setDisabled(false);
        project.setClient(employee);
        project = projectRepository.save(project);

        briefing = new Briefing();
        briefing.setBriefingType(briefingType);
        briefing.setProject(project);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing para teste");
        briefing = briefingRepository.save(briefing);

        measurement = new Measurement();
        measurement.setBriefing(briefing);
        measurement.setHeight(BigDecimal.valueOf(1.75));
        measurement.setLength(BigDecimal.valueOf(2.50));
    }

    @Test
    @DisplayName("You must save and load a Measurement")
    void shouldSaveAndLoadMeasurement() {
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertThat(savedMeasurement.getId()).isNotNull();

        Measurement foundMeasurement = measurementRepository.findById(savedMeasurement.getId()).orElse(null);
        assertThat(foundMeasurement).isNotNull();
        assertThat(foundMeasurement.getHeight()).isEqualTo(measurement.getHeight());
        assertThat(foundMeasurement.getLength()).isEqualTo(measurement.getLength());
        assertThat(foundMeasurement.getBriefing()).isEqualTo(briefing);
    }

    @Test
    @DisplayName("You must delete a Measurement")
    void shouldDeleteMeasurement() {
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertThat(savedMeasurement.getId()).isNotNull();

        measurementRepository.delete(savedMeasurement);

        Measurement foundMeasurement = measurementRepository.findById(savedMeasurement.getId()).orElse(null);
        assertThat(foundMeasurement).isNull();
    }
}