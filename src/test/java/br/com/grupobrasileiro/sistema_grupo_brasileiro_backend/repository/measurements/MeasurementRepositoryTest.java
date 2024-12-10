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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class MeasurementRepositoryTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    private Measurement measurement;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurações de mock
        Profile profile = new Profile();
        profile.setDescription("Perfil de Teste");
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setDisabled(false);
        user.setProfile(profile);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Employee employee = new Employee();
        employee.setName("Cliente Teste");
        employee.setLastName("Sobrenome Teste");
        employee.setPhoneNumber("123456789");
        employee.setSector("Vendas");
        employee.setOccupation("Gerente");
        employee.setAgency("Agência Central");
        employee.setAvatar(1L);
        employee.setUser(user);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("Tipo de briefing de teste");
        when(briefingTypeRepository.save(any(BriefingType.class))).thenReturn(briefingType);

        Project project = new Project();
        project.setTitle("Título do Projeto de Teste");
        project.setDisabled(false);
        project.setClient(employee);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        briefing = new Briefing();
        briefing.setBriefingType(briefingType);
        briefing.setProject(project);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefing.setDetailedDescription("Descrição detalhada do briefing para teste");
        when(briefingRepository.save(any(Briefing.class))).thenReturn(briefing);

        measurement = new Measurement();
        measurement.setId(1L);
        measurement.setBriefing(briefing);
        measurement.setHeight(BigDecimal.valueOf(1.75));
        measurement.setLength(BigDecimal.valueOf(2.50));
    }

    @Test
    @DisplayName("You must save and load a Measurement")
    void shouldSaveAndLoadMeasurement() {
        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(measurementRepository.findById(anyLong())).thenReturn(Optional.of(measurement));

        Measurement savedMeasurement = measurementRepository.save(measurement);

        Measurement foundMeasurement = measurementRepository.findById(savedMeasurement.getId()).orElse(null);
        assertThat(foundMeasurement).isNotNull();
        assertThat(foundMeasurement.getHeight()).isEqualTo(measurement.getHeight());
        assertThat(foundMeasurement.getLength()).isEqualTo(measurement.getLength());
        assertThat(foundMeasurement.getBriefing()).isEqualTo(briefing);
    }

    @Test
    @DisplayName("You must delete a Measurement")
    void shouldDeleteMeasurement() {
        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        measurementRepository.save(measurement);
        when(measurementRepository.findById(measurement.getId())).thenReturn(Optional.of(measurement));

        measurementRepository.delete(measurement);

        when(measurementRepository.findById(measurement.getId())).thenReturn(Optional.empty());
        Measurement foundMeasurement = measurementRepository.findById(measurement.getId()).orElse(null);
        assertThat(foundMeasurement).isNull();
    }
}
