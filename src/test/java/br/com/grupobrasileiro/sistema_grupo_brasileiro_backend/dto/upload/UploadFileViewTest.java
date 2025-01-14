package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UploadFileViewTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUploadFileView() {
        // Arrange
        UploadFileView uploadFileView = new UploadFileView("valid_file.txt", "http://example.com/file", "text/plain", 1024L);

        // Act
        Set<ConstraintViolation<UploadFileView>> violations = validator.validate(uploadFileView);

        // Assert
        assertTrue(violations.isEmpty(), "Should be valid with no constraint violations");
    }

    @Test
    public void testFileNameNotBlank() {
        // Arrange
        UploadFileView uploadFileView = new UploadFileView("", "http://example.com/file", "text/plain", 1024L);

        // Act
        Set<ConstraintViolation<UploadFileView>> violations = validator.validate(uploadFileView);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("O nome do arquivo não pode estar vazio", violations.iterator().next().getMessage());
    }

    @Test
    public void testFileDownloadUriNotBlank() {
        // Arrange
        UploadFileView uploadFileView = new UploadFileView("valid_file.txt", "", "text/plain", 1024L);

        // Act
        Set<ConstraintViolation<UploadFileView>> violations = validator.validate(uploadFileView);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("A URI de download do arquivo não pode estar vazia", violations.iterator().next().getMessage());
    }

    @Test
    public void testFileTypeNotBlank() {
        // Arrange
        UploadFileView uploadFileView = new UploadFileView("valid_file.txt", "http://example.com/file", "", 1024L);

        // Act
        Set<ConstraintViolation<UploadFileView>> violations = validator.validate(uploadFileView);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("O tipo de arquivo não pode estar vazio", violations.iterator().next().getMessage());
    }

    @Test
    public void testSizeNotNull() {
        // Arrange
        UploadFileView uploadFileView = new UploadFileView("valid_file.txt", "http://example.com/file", "text/plain", null);

        // Act
        Set<ConstraintViolation<UploadFileView>> violations = validator.validate(uploadFileView);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("O tamanho do arquivo não pode ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testFileNameSize() {
        // Arrange
        String longFileName = "a".repeat(256); // 256 characters long
        UploadFileView uploadFileView = new UploadFileView(longFileName, "http://example.com/file", "text/plain", 1024L);

        // Act
        Set<ConstraintViolation<UploadFileView>> violations = validator.validate(uploadFileView);

        // Assert
        assertEquals(1, violations.size());
        assertEquals("O nome do arquivo deve ter no máximo 255 caracteres", violations.iterator().next().getMessage());
    }
}
