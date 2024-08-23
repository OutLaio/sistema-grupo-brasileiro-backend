package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

import java.util.Optional;

public class ProjectFormMapperTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectFormMapper projectFormMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void testMap() {
        // Arrange
        Long clientId = faker.number().randomNumber();
        User client = new User(clientId, faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(), 
            faker.company().industry(), faker.job().title(), faker.code().isbn13(), faker.internet().emailAddress(), 
            faker.internet().password(), faker.number().randomDigitNotZero());
        
        ProjectForm projectForm = new ProjectForm(
            faker.lorem().sentence(), 
            faker.lorem().paragraph()
        );

        when(userRepository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        Project project = projectFormMapper.map(projectForm);

        // Assert
        assertNotNull(project, "O projeto não deve ser nulo");
        assertEquals(projectForm.title(), project.getTitle(), "O título do projeto deve corresponder ao título do ProjectForm");
        assertEquals(projectForm.description(), project.getDescription(), "A descrição do projeto deve corresponder à descrição do ProjectForm");

     
        assertNotNull(project.getUsers(), "A lista de usuários não deve ser nula");
        assertTrue(project.getUsers().isEmpty(), "O projeto deve ter 0 usuários devido à implementação do mapper");
    }

    @Test
    void testMap_UserNotFound() {
        // Arrange
        Long clientId = faker.number().randomNumber();
        
        ProjectForm projectForm = new ProjectForm(
            faker.lorem().sentence(), 
            faker.lorem().paragraph()
        );

        Project project = projectFormMapper.map(projectForm);

        // Assert
        assertNotNull(project, "O projeto não deve ser nulo");
        assertEquals(projectForm.title(), project.getTitle(), "O título do projeto deve corresponder ao título do ProjectForm");
        assertEquals(projectForm.description(), project.getDescription(), "A descrição do projeto deve corresponder à descrição do ProjectForm");
        
        // Check if the project contains users
        assertNotNull(project.getUsers(), "A lista de usuários não deve ser nula");
        assertEquals(0, project.getUsers().size(), "O projeto deve ter 0 usuários porque o ProjectUser não foi adicionado");

    }
}