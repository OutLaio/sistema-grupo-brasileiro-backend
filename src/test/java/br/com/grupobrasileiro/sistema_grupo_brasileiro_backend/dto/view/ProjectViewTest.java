package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectViewTest {

    @Test
    void testProjectViewCreation() {
        Long id = 1L;
        String title = "Project Title";
        String description = "Project Description";
        Integer progress = 50;
        String status = "In Progress";
        Long projectUserId = 2L;

        ProjectView projectView = new ProjectView(id, title, description, progress, status, projectUserId);

        assertEquals(id, projectView.id());
        assertEquals(title, projectView.title());
        assertEquals(description, projectView.description());
        assertEquals(progress, projectView.progress());
        assertEquals(status, projectView.status());
        assertEquals(projectUserId, projectView.projectUserId());
    }
}
