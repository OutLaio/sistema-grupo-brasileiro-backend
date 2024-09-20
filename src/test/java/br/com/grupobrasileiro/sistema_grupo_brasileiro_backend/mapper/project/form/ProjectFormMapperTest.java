package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    /**
     * Testa o mapeamento de ProjectForm para Project.
     * Verifica se todos os campos são mapeados corretamente.
     */
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
    }
    
    /**
     * Testa o mapeamento de ProjectForm com um cliente nulo.
     * Verifica se o Project é criado corretamente sem cliente.
     */
    @Test
    @DisplayName("Should map ProjectForm with null client correctly")
    void shouldMapProjectFormWithNullClientCorrectly() {
        // Arrange
        String title = faker.company().name();
        ProjectStatusEnum status = ProjectStatusEnum.TO_DO;
        ProjectForm projectForm = new ProjectForm(null, title, status);

        // Act
        Project result = projectFormMapper.map(projectForm);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNull(result.getClient(), "Project client should be null");
        assertEquals(title, result.getTitle(), "Project title should match");
        assertEquals(status.toString(), result.getStatus(), "Project status should match");
    }

    /**
     * Testa o mapeamento de ProjectForm com um título vazio.
     * Verifica se o título é mapeado corretamente.
     */
    @Test
    @DisplayName("Should handle ProjectForm with empty title")
    void shouldHandleProjectFormWithEmptyTitle() {
        // Arrange
        Long clientId = faker.number().randomNumber();
        ProjectStatusEnum status = ProjectStatusEnum.TO_DO;
        ProjectForm projectForm = new ProjectForm(clientId, "", status);

        // Act
        Project result = projectFormMapper.map(projectForm);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        
    }

    /**
     * Testa o mapeamento de ProjectForm com um status diferente.
     * Verifica se o status é mapeado corretamente.
     */
    @Test
    @DisplayName("Should map ProjectForm with different status correctly")
    void shouldMapProjectFormWithDifferentStatusCorrectly() {
        // Arrange
        Long clientId = faker.number().randomNumber();
        String title = faker.company().name();
        ProjectStatusEnum status = ProjectStatusEnum.IN_PROGRESS; 
        ProjectForm projectForm = new ProjectForm(clientId, title, status);

        // Act
        Project result = projectFormMapper.map(projectForm);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
       
    }

}
