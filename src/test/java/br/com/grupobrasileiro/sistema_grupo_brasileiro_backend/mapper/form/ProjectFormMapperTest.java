package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserConverter;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

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
        assertNotNull(project);
        assertEquals(projectForm.title(), project.getTitle());
        assertEquals(projectForm.description(), project.getDescription());
        assertEquals(1, project.getUsers().size());

        ProjectUser projectUser = project.getUsers().iterator().next();
        assertEquals(client, projectUser.getClient());
        assertEquals(project, projectUser.getProject());
    }


      

    @Test
    void testMap_UserNotFound() {
        // Arrange
        Long clientId = faker.number().randomNumber();
        
        
        ProjectForm projectForm = new ProjectForm(
            faker.lorem().sentence(), 
            faker.lorem().paragraph()
        );

        when(userRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            projectFormMapper.map(projectForm);
        });
        assertEquals("Cliente não encontrado com o ID: " + clientId, thrown.getMessage());
    }

}
