package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserConverter;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

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

public class ProjectFormMapperTest {

    @InjectMocks
    private ProjectFormMapper projectFormMapper;

    public ProjectFormMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        // Create a set of UserForms with dummy data
        Set<UserForm> userForms = new HashSet<>();
        userForms.add(new UserForm(
            "John", "Doe", "+55 (11) 98888-8888", "IT", "Developer", "1234", "john.doe@example.com", "P@ssw0rd", RoleEnum.ROLE_COLLABORATOR.getCode()
        ));
        userForms.add(new UserForm(
            "Jane", "Smith", "+55 (21) 98888-8888", "HR", "Manager", "5678", "jane.smith@example.com", "P@ssw0rd123", RoleEnum.ROLE_CLIENT.getCode()
        ));

        // Create a ProjectForm instance with dummy data
        ProjectForm projectForm = new ProjectForm(
            "New Project",        // title
            "Project Description", // description
            50,                   // progress
            "In Progress",        // status
            userForms             // projectUsers
        );

        // Map ProjectForm to Project
        Project project = projectFormMapper.map(projectForm);

        // Verify that Project fields are mapped correctly
        assertEquals("New Project", project.getTitle());
        assertEquals("Project Description", project.getDescription());
        assertEquals(50, project.getProgress());
        assertEquals("In Progress", project.getStatus());

        // Verify that users are mapped correctly
        assertNotNull(project.getUsers()); 
        assertEquals(2, project.getUsers().size()); 

        // Verify that users are mapped correctly to ProjectUser
        for (ProjectUser projectUser : project.getUsers()) {
            if (projectUser.getClient() != null) {
                User client = projectUser.getClient();
                assertNotNull(client.getName());
                assertNotNull(client.getLastname());
                assertNotNull(client.getPhonenumber());
                assertNotNull(client.getSector());
                assertNotNull(client.getOccupation());
                assertNotNull(client.getNop());
                assertNotNull(client.getEmail());
                assertNotNull(client.getRole());
            }
            if (projectUser.getCollaborator() != null) {
                User collaborator = projectUser.getCollaborator();
                assertNotNull(collaborator.getName());
                assertNotNull(collaborator.getLastname());
                assertNotNull(collaborator.getPhonenumber());
                assertNotNull(collaborator.getSector());
                assertNotNull(collaborator.getOccupation());
                assertNotNull(collaborator.getNop());
                assertNotNull(collaborator.getEmail());
                assertNotNull(collaborator.getRole());
            }
        }

        // Verify that roles are mapped correctly to ProjectUser
        for (ProjectUser projectUser : project.getUsers()) {
            if (projectUser.getClient() != null && projectUser.getClient().getRole().equals(RoleEnum.ROLE_COLLABORATOR.getCode())) {
                assertNotNull(projectUser.getCollaborator());
                assertNull(projectUser.getClient());
            } else if (projectUser.getClient() != null && projectUser.getClient().getRole().equals(RoleEnum.ROLE_CLIENT.getCode())) {
                assertNotNull(projectUser.getClient());
                assertNull(projectUser.getCollaborator());
            }
        }
    }
}
