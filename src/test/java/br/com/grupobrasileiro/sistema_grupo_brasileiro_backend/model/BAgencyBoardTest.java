package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BAgencyBoardTest {

    @Test
    void testBAgencyBoardCreation() {
        // Arrange
        Long id = 1L;
        String boardLocation = "Location A";
        Boolean companySharing = true;
        String boardType = "Type A";
        String material = "Material A";
        String observations = "Some observations";
        Project project = new Project();  // Assumindo que Project tem um construtor padrão

        // Act
        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, boardLocation, companySharing, boardType, material, observations, project);

        // Assert
        assertThat(bAgencyBoard.getId()).isEqualTo(id);
        assertThat(bAgencyBoard.getBoardLocation()).isEqualTo(boardLocation);
        assertThat(bAgencyBoard.getCompanySharing()).isEqualTo(companySharing);
        assertThat(bAgencyBoard.getBoardType()).isEqualTo(boardType);
        assertThat(bAgencyBoard.getMaterial()).isEqualTo(material);
        assertThat(bAgencyBoard.getObservations()).isEqualTo(observations);
        assertThat(bAgencyBoard.getProject()).isEqualTo(project);
    }

    @Test
    void testSetProject() {
        // Arrange
        Project project = new Project();
        BAgencyBoard bAgencyBoard = new BAgencyBoard();

        // Act
        bAgencyBoard.setProject(project);

        // Assert
        assertThat(bAgencyBoard.getProject()).isEqualTo(project);
    }

    @Test
    void testSetId() {
        // Arrange
        Long id = 1L;
        BAgencyBoard bAgencyBoard = new BAgencyBoard();

        // Act
        bAgencyBoard.setId(id);

        // Assert
        assertThat(bAgencyBoard.getId()).isEqualTo(id);
    }

    @Test
    void testBAgencyBoardWithNullFields() {
        // Arrange
        Long id = 1L;
        String boardLocation = null;
        Boolean companySharing = null;
        String boardType = null;
        String material = null;
        String observations = null;
        Project project = null;

        // Act
        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, boardLocation, companySharing, boardType, material, observations, project);

        // Assert
        assertThat(bAgencyBoard.getId()).isEqualTo(id);
        assertThat(bAgencyBoard.getBoardLocation()).isNull();
        assertThat(bAgencyBoard.getCompanySharing()).isNull();
        assertThat(bAgencyBoard.getBoardType()).isNull();
        assertThat(bAgencyBoard.getMaterial()).isNull();
        assertThat(bAgencyBoard.getObservations()).isNull();
        assertThat(bAgencyBoard.getProject()).isNull();
    }

    @Test
    void testUpdateFields() {
        // Arrange
        Long id = 1L;
        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, "Location A", true, "Type A", "Material A", "Observations A", new Project());

        // Act
        bAgencyBoard.setBoardLocation("Location B");
        bAgencyBoard.setCompanySharing(false);
        bAgencyBoard.setBoardType("Type B");
        bAgencyBoard.setMaterial("Material B");
        bAgencyBoard.setObservations("Observations B");

        // Assert
        assertThat(bAgencyBoard.getBoardLocation()).isEqualTo("Location B");
        assertThat(bAgencyBoard.getCompanySharing()).isEqualTo(false);
        assertThat(bAgencyBoard.getBoardType()).isEqualTo("Type B");
        assertThat(bAgencyBoard.getMaterial()).isEqualTo("Material B");
        assertThat(bAgencyBoard.getObservations()).isEqualTo("Observations B");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Long id1 = 1L;
        Long id2 = 2L;

        BAgencyBoard board1 = new BAgencyBoard(id1, "Location A", true, "Type A", "Material A", "Observations A", new Project());
        BAgencyBoard board2 = new BAgencyBoard(id1, "Location A", true, "Type A", "Material A", "Observations A", new Project());
        BAgencyBoard board3 = new BAgencyBoard(id2, "Location B", false, "Type B", "Material B", "Observations B", new Project());

        // Act & Assert
        assertThat(board1).isEqualTo(board2);  // should be equal
        assertThat(board1).isNotEqualTo(board3);  // should not be equal

        // Check if hash codes are consistent
        assertThat(board1.hashCode()).isEqualTo(board2.hashCode());
        assertThat(board1.hashCode()).isNotEqualTo(board3.hashCode());
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard(1L, "Location A", true, "Type A", "Material A", "Observations A", new Project());

        // Act & Assert
        assertThat(bAgencyBoard).isNotEqualTo(null);
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard(1L, "Location A", true, "Type A", "Material A", "Observations A", new Project());
        String otherObject = "Not a BAgencyBoard";

        // Act & Assert
        assertThat(bAgencyBoard).isNotEqualTo(otherObject);
    }

    @Test
    void testToString() {
        // Arrange
        Long id = 1L;
        BAgencyBoard bAgencyBoard = new BAgencyBoard(id, "Location A", true, "Type A", "Material A", "Observations A", new Project());

        // Act
        String result = bAgencyBoard.toString();

        // Assert
        assertThat(result).isEqualTo("BAgencyBoard{id=" + id + "}");
    }
}
