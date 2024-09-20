package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.project.ProjectController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ApproveForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.VersionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private VersionService versionService;

    @InjectMocks
    private ProjectController projectController;

    public ProjectControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Assign Collaborator - Success")
    void assignCollaborator_Success() {
        Long projectId = 1L;
        AssignCollaboratorForm form = new AssignCollaboratorForm(projectId/* Preencher com dados válidos */);
        
        ResponseEntity<?> response = projectController.assignCollaborator(projectId, form);

        verify(projectService, times(1)).assignCollaborator(eq(projectId), eq(form));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Assign Collaborator - Throws Exception")
    void assignCollaborator_ThrowsException() {
        Long projectId = 1L;
        AssignCollaboratorForm form = new AssignCollaboratorForm(projectId/* Preencher com dados válidos */);
        doThrow(new RuntimeException("Error assigning collaborator"))
                .when(projectService).assignCollaborator(any(Long.class), any(AssignCollaboratorForm.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.assignCollaborator(projectId, form);
        });

        verify(projectService, times(1)).assignCollaborator(eq(projectId), eq(form));
        assertEquals("Error assigning collaborator", exception.getMessage());
    }

    @Test
    @DisplayName("Create New Version - Success")
    void newVersion_Success() {
        Long projectId = 1L;
        NewVersionForm form = new NewVersionForm("v2");
        
        ResponseEntity<?> response = projectController.newVersion(projectId, form);

        verify(versionService, times(1)).create(eq(projectId), eq(form));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Create New Version - Throws Exception")
    void newVersion_ThrowsException() {
        Long projectId = 1L;
        NewVersionForm form = new NewVersionForm("v2");
        doThrow(new RuntimeException("Error creating version"))
                .when(versionService).create(any(Long.class), any(NewVersionForm.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.newVersion(projectId, form);
        });

        verify(versionService, times(1)).create(eq(projectId), eq(form));
        assertEquals("Error creating version", exception.getMessage());
    }

    @Test
    @DisplayName("Supervisor Approve - Success")
    void supervisorApprove_Success() {
        ApproveForm form = new ApproveForm(1L, 2L, true, "Looks good");
        
        ResponseEntity<?> response = projectController.supervisorApprove(form);

        verify(versionService, times(1)).supervisorApprove(eq(form));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Supervisor Approve - Throws Exception")
    void supervisorApprove_ThrowsException() {
        ApproveForm form = new ApproveForm(1L, 2L, true, "Looks good");
        doThrow(new RuntimeException("Error in supervisor approval"))
                .when(versionService).supervisorApprove(any(ApproveForm.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.supervisorApprove(form);
        });

        verify(versionService, times(1)).supervisorApprove(eq(form));
        assertEquals("Error in supervisor approval", exception.getMessage());
    }

    @Test
    @DisplayName("Client Approve - Success")
    void clientApprove_Success() {
        ApproveForm form = new ApproveForm(1L, 2L, true, "Looks good");
        
        ResponseEntity<?> response = projectController.clientApprove(form);

        verify(versionService, times(1)).clientApprove(eq(form));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Client Approve - Throws Exception")
    void clientApprove_ThrowsException() {
        ApproveForm form = new ApproveForm(1L, 2L, true, "Looks good");
        doThrow(new RuntimeException("Error in client approval"))
                .when(versionService).clientApprove(any(ApproveForm.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.clientApprove(form);
        });

        verify(versionService, times(1)).clientApprove(eq(form));
        assertEquals("Error in client approval", exception.getMessage());
    }

    @Test
    @DisplayName("Set Has Production - Success")
    void hasProduction_Success() {
        Long projectId = 1L;
        Boolean hasConfection = true;
        
        ResponseEntity<?> response = projectController.hasProduction(projectId, hasConfection);

        verify(projectService, times(1)).setHasConfection(eq(projectId), eq(hasConfection));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Set Has Production - Throws Exception")
    void hasProduction_ThrowsException() {
        Long projectId = 1L;
        Boolean hasConfection = true;
        doThrow(new RuntimeException("Error setting production status"))
                .when(projectService).setHasConfection(any(Long.class), any(Boolean.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.hasProduction(projectId, hasConfection);
        });

        verify(projectService, times(1)).setHasConfection(eq(projectId), eq(hasConfection));
        assertEquals("Error setting production status", exception.getMessage());
    }

    @Test
    @DisplayName("Finish Project - Success")
    void finish_Success() {
        Long projectId = 1L;
        
        ResponseEntity<?> response = projectController.finish(projectId);

        verify(projectService, times(1)).setFinished(eq(projectId));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Finish Project - Throws Exception")
    void finish_ThrowsException() {
        Long projectId = 1L;
        doThrow(new RuntimeException("Error finishing project"))
                .when(projectService).setFinished(any(Long.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.finish(projectId);
        });

        verify(projectService, times(1)).setFinished(eq(projectId));
        assertEquals("Error finishing project", exception.getMessage());
    }

    @Test
    @DisplayName("Set Project to Standby - Success")
    void standby_Success() {
        Long projectId = 1L;
        
        ResponseEntity<?> response = projectController.standby(projectId);

        verify(projectService, times(1)).setStandby(eq(projectId));
        assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    @DisplayName("Set Project to Standby - Throws Exception")
    void standby_ThrowsException() {
        Long projectId = 1L;
        doThrow(new RuntimeException("Error setting project to standby"))
                .when(projectService).setStandby(any(Long.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            projectController.standby(projectId);
        });

        verify(projectService, times(1)).setStandby(eq(projectId));
        assertEquals("Error setting project to standby", exception.getMessage());
    }
}
