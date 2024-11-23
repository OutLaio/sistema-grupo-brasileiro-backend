package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.upload;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload.UploadFileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload.FileStorageService;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @InjectMocks
    private FileController fileController;

    @Mock
    private FileStorageService fileStorageService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    
    @Test
    public void testUploadMultipleFiles() throws Exception {
        // Arrange
        MockMultipartFile file1 = new MockMultipartFile("files", "file1.txt", "text/plain", "Content 1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt", "text/plain", "Content 2".getBytes());

        UploadFileView uploadFileView1 = new UploadFileView("file1.txt", "/api/v1/file/downloadFile/file1.txt", "text/plain", 10L);
        UploadFileView uploadFileView2 = new UploadFileView("file2.txt", "/api/v1/file/downloadFile/file2.txt", "text/plain", 10L);
        when(fileStorageService.storeFile(file1)).thenReturn("file1.txt");
        when(fileStorageService.storeFile(file2)).thenReturn("file2.txt");

        // Act & Assert
        mockMvc.perform(multipart("/api/v1/file/uploadMultipleFiles")
                .file(file1)
                .file(file2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].fileName").value("file1.txt"))
                .andExpect(jsonPath("$[1].fileName").value("file2.txt"));
    }

    @Test
    public void testDownloadFile() throws Exception {
        // Arrange
        String fileName = "testfile.txt";
        // Simulate a resource from the FileStorageService
        Resource resource = mock(Resource.class);
        when(fileStorageService.loadFileAsResource(fileName)).thenReturn((Pair<ByteArrayResource, String>) resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getFilename()).thenReturn(fileName);

        // Act & Assert
        mockMvc.perform(get("/api/v1/file/downloadFile/{fileName}", fileName))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\""));
    }
}
