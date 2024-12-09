package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.FileStorageException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.MyFileNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.SShClientException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockito.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    @Mock
    private SSHClient sshClient;
    
    @Mock
    private SFTPClient sftpClient;
    
    @Mock
    private InputStream fileInputStream;

    @InjectMocks
    private FileStorageService fileStorageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   
    @Test
    void testStoreFileWithInvalidPath() throws Exception {
        // Test invalid file name with path components (..)
        String fileName = "invalid/../file.txt";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(fileName);

        assertThrows(FileStorageException.class, () -> fileStorageService.storeFile(file));
    }

    @Test
    void testStoreFileException() throws Exception {
        // Mocking exception during file storage
        String fileName = "testfile.txt";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(fileName);
        when(file.getInputStream()).thenThrow(new IOException("Error"));

        assertThrows(FileStorageException.class, () -> fileStorageService.storeFile(file));
    }

    @Test
    void testUploadToSFTPException() throws IOException {
        // Mocking SFTP client failure with doThrow for void method
        when(sshClient.newSFTPClient()).thenReturn(sftpClient);
        doThrow(new IOException("SFTP Error")).when(sftpClient).put(any(InMemorySourceFile.class), anyString());

        assertThrows(SShClientException.class, () -> fileStorageService.storeFile(mock(MultipartFile.class)));
    }

    
   

    @Test
    void testLoadFileAsResourceNotFound() throws IOException {
        // Simulate error when the file is not found
        when(sshClient.newSFTPClient()).thenThrow(new IOException("File not found"));

        assertThrows(MyFileNotFoundException.class, () -> fileStorageService.loadFileAsResource("nonexistentfile.txt"));
    }
}
