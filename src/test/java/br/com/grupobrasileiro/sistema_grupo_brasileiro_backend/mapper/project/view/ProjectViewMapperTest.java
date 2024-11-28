package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeSimpleViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  
class ProjectViewMapperTest {

    @Mock
    private EmployeeSimpleViewMapper employeeSimpleViewMapper;

    @InjectMocks
    private ProjectViewMapper projectViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

  

    @Test
    @DisplayName("Should map projectForm with null collaborator")
    void mapProjectWithNullCollaborator() {
        // Criando o usuário e cliente
        User clientUser = new User();
        clientUser.setId(1L);
        clientUser.setEmail(faker.internet().emailAddress());
        clientUser.setPassword(faker.internet().password());
        clientUser.setDisabled(false);

        Employee client = new Employee(1L, faker.name().firstName(), faker.name().lastName(), 
                "123456789", "IT", "Developer", "Agency", 1L, clientUser, new HashSet<>(), new HashSet<>(), new HashSet<>());

        // Criando o projeto com collaborator null
        Project project = new Project(1L, null, client, "Project Title", "ACTIVE", false, mock(Briefing.class));

        // Criando a visão do cliente
        EmployeeSimpleView clientView = new EmployeeSimpleView(1L, "John Doe", 1L);
        when(employeeSimpleViewMapper.map(client)).thenReturn(clientView);

        assertDoesNotThrow(() -> {
            ProjectView result = projectViewMapper.map(project);
            assertNotNull(result, "Mapped Project should not be null");
            assertNotNull(result.client(), "Project client should not be null");
            assertEquals(clientView.id(), result.client().id(), "Project client ID should match");
            assertNull(result.collaborator(), "Project collaborator should be null");
            assertEquals("Project Title", result.title(), "Project title should match");
            assertEquals("ACTIVE", result.status(), "Project status should match");
            assertNull(result.briefingType(), "Project briefingType should be null when briefingType is null");
        });
    }

  
    @Test
    @DisplayName("Should map valid projectForm correctly with valid Briefing")
    void shouldMapValidProject() {
        // Criando o tipo de briefing
        BriefingType briefingType = new BriefingType(1L, "Type A");

        // Criando o briefing com dados válidos
        Briefing briefing = new Briefing();
        briefing.setId(1L);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(10));
        briefing.setDetailedDescription("Detailed description");

        // Criando o projeto com o briefing
        Project project = new Project(1L, null, null, "Valid Project", "ACTIVE", false, briefing);

        // Mapeando o projeto para o ProjectView
        ProjectView result = projectViewMapper.map(project);

        // Verificando o resultado
        assertNotNull(result);
        assertEquals("Valid Project", result.title());
        assertEquals("ACTIVE", result.status());

        // Verificando o tipo de briefing
        assertNotNull(result.briefingType());
        assertEquals("Type A", result.briefingType());  
    }


}
