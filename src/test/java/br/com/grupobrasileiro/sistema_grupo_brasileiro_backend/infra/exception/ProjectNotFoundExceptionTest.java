//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;
//
//public class ProjectNotFoundExceptionTest {
//
//    @Test
//    public void testExceptionMessage() {
//        // Expected message for the exception
//        String expectedMessage = "Project not found";
//
//        // Simulate a method that would throw ProjectNotFoundException
//        ProjectService projectService = new ProjectService();
//
//        // Verify that the exception is thrown and the message matches the expected message
//        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () -> {
//            projectService.getProjectById(999L);
//        });
//
//        // Verify that the exception message matches the expected message
//        assertEquals(expectedMessage, exception.getMessage(),
//                     "The exception message should match the expected message");
//    }
//}
