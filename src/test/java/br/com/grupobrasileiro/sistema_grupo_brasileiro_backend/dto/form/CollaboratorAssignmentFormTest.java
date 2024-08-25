package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CollaboratorAssignmentFormTest {

    @Test
    public void testValidCollaboratorAssignmentForm() {
        CollaboratorAssignmentForm form = new CollaboratorAssignmentForm(1L);

        assertNotNull(form, "Form should not be null");
        assertEquals(1L, form.collaboratorId(), "The collaboratorId should be 1L");
    }

    @Test
    public void testCollaboratorIdIsNull() {
        CollaboratorAssignmentForm form = new CollaboratorAssignmentForm(null);

        assertNotNull(form, "Form should not be null");
        assertNull(form.collaboratorId(), "The collaboratorId should be null");
    }

    @Test
    public void testCollaboratorIdIsNegative() {
        CollaboratorAssignmentForm form = new CollaboratorAssignmentForm(-1L);

        assertNotNull(form, "Form should not be null");
        assertEquals(-1L, form.collaboratorId(), "The collaboratorId should be -1L");
    }
}
