package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.FileStorageException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.MyFileNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.upload.FileStorageProperties;

@ExtendWith(MockitoExtension.class)
public class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    @Mock
    private FileStorageProperties fileStorageProperties;

    private Path testDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Configura o diretório de upload temporário para os testes
        testDir = Files.createTempDirectory("testUploads");
        when(fileStorageProperties.getUploadDir()).thenReturn(testDir.toString());

        // Instancia o serviço manualmente com o mock configurado
        fileStorageService = new FileStorageService(fileStorageProperties);
    }

    @Test
    public void testStoreFile_withValidFile() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "Hello World".getBytes());

        // Act
        String fileName = fileStorageService.storeFile(file);

        // Assert
        Path targetPath = testDir.resolve(fileName);
        assertTrue(Files.exists(targetPath), "File should be stored");
        assertEquals("testfile.txt", fileName);
    }


    @Test
    public void testStoreFile_withInvalidFileName() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "../invalid.txt", "text/plain", "Hello World".getBytes());

        // Act & Assert
        assertThrows(FileStorageException.class, () -> fileStorageService.storeFile(file), "Should throw FileStorageException for invalid file name");
    }

    @Test
    public void testLoadFileAsResource_withExistingFile() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "Hello World".getBytes());
        fileStorageService.storeFile(file);

        // Act
        Resource resource = fileStorageService.loadFileAsResource("testfile.txt");

        // Assert
        assertNotNull(resource);
        assertTrue(resource.exists(), "Resource should exist");
    }

    @Test
    public void testLoadFileAsResource_withNonExistingFile() {
        // Act & Assert
        assertThrows(MyFileNotFoundException.class, () -> fileStorageService.loadFileAsResource("nonexistent.txt"), "Should throw MyFileNotFoundException for non-existing file");
    }

    }