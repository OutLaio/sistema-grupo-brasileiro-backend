package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectViewMapperTest {

    private ProjectViewMapper projectViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        projectViewMapper = new ProjectViewMapper();
        faker = new Faker();
    }



   

    @Test
    void shouldMapProjectWithEmptyUserSet() {
        // Arrange
        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(new HashSet<>()); // Empty set

        // Act
        ProjectView projectView = projectViewMapper.map(project);

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "Project ID should match");
        assertEquals(project.getTitle(), projectView.title(), "Project Title should match");
        assertEquals(project.getDescription(), projectView.description(), "Project Description should match");
        assertEquals(project.getProgress(), projectView.progress(), "Project Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Project Status should match");
        assertNull(projectView.projectUserId(), "ProjectUserId should be null when user set is empty");
    }

    @Test
    void shouldMapProjectWithProjectUserWithoutClient() {
        // Arrange
        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(null); // No client

        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser);

        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(projectUsers);

        // Act
        ProjectView projectView = projectViewMapper.map(project);

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "Project ID should match");
        assertEquals(project.getTitle(), projectView.title(), "Project Title should match");
        assertEquals(project.getDescription(), projectView.description(), "Project Description should match");
        assertEquals(project.getProgress(), projectView.progress(), "Project Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Project Status should match");
        assertNull(projectView.projectUserId(), "ProjectUserId should be null when client is not present");
    }

    
}
