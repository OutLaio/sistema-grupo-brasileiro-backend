package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

class ProjectFormMapperTest {

    private ProjectFormMapper projectFormMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        projectFormMapper = new ProjectFormMapper();
        faker = new Faker();
    }

    @Test
    @DisplayName("Should map ProjectForm to Project")
    void shouldMapProjectFormToProject() {
        // Arrange
        Long clientId = faker.number().randomNumber();
        String title = faker.company().name();
        ProjectStatusEnum status = ProjectStatusEnum.TO_DO;

        ProjectForm projectForm = new ProjectForm(clientId, title, status);

        // Act
        Project result = projectFormMapper.map(projectForm);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNotNull(result.getClient(), "Project client should not be null");
        assertEquals(clientId, result.getClient().getId(), "Project client ID should match");
        assertEquals(title, result.getTitle(), "Project title should match");
        assertEquals(status.toString(), result.getStatus(), "Project status should match");
        assertFalse(result.getDisabled(), "Project should not be marked as disabled");
    }
}
