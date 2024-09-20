//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
//import jakarta.transaction.Transactional;
//
//@DataJpaTest
//@ActiveProfiles("test")
//@Transactional
//public class ProjectRepositoryTest {
//
//    @Autowired
//    private EmployeeRepository projectRepository;
//
//    @Test
//    @Rollback(false)
//    public void testSaveAndFindProject() {
//        // Arrange
//        Project project = new Project();
//        project.setTitle("Test Project");
//        project.setDescription("Description for Test Project");
//        project.setProgress(0);
//        project.setStatus("New");
//
//        // Act
//        Project savedProject = projectRepository.save(project);
//        Project foundProject = projectRepository.findById(savedProject.getId()).orElse(null);
//
//        // Assert
//        assertThat(foundProject).isNotNull();
//        assertThat(foundProject.getTitle()).isEqualTo("Test Project");
//        assertThat(foundProject.getDescription()).isEqualTo("Description for Test Project");
//        assertThat(foundProject.getProgress()).isEqualTo(0);
//        assertThat(foundProject.getStatus()).isEqualTo("New");
//    }
//
//    @Test
//    public void testFindByIdNotFound() {
//        // Act
//        Project foundProject = projectRepository.findById(Long.MAX_VALUE).orElse(null);
//
//        // Assert
//        assertThat(foundProject).isNull();
//    }
//
//    @Test
//    @Rollback(false)
//    public void testUpdateProject() {
//        // Arrange
//        Project project = new Project();
//        project.setTitle("Original Title");
//        project.setDescription("Original Description");
//        project.setProgress(0);
//        project.setStatus("New");
//        Project savedProject = projectRepository.save(project);
//
//        // Act
//        savedProject.setTitle("Updated Title");
//        savedProject.setDescription("Updated Description");
//        savedProject.setProgress(50);
//        savedProject.setStatus("In Progress");
//        Project updatedProject = projectRepository.save(savedProject);
//        Project foundProject = projectRepository.findById(updatedProject.getId()).orElse(null);
//
//        // Assert
//        assertThat(foundProject).isNotNull();
//        assertThat(foundProject.getTitle()).isEqualTo("Updated Title");
//        assertThat(foundProject.getDescription()).isEqualTo("Updated Description");
//        assertThat(foundProject.getProgress()).isEqualTo(50);
//        assertThat(foundProject.getStatus()).isEqualTo("In Progress");
//    }
//}
