package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.upload;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload.UploadFileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload.FileStorageService;

@SpringBootTest
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
        MockMultipartFile file1 = new MockMultipartFile("files", "file1.txt", "text/plain", "Content 1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt", "text/plain", "Content 2".getBytes());

        when(fileStorageService.storeFile(file1)).thenReturn("file1.txt");
        when(fileStorageService.storeFile(file2)).thenReturn("file2.txt");

        mockMvc.perform(multipart("/api/v1/file/uploadMultipleFiles")
                .file(file1)
                .file(file2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].fileName").value("file1.txt"))
                .andExpect(jsonPath("$[1].fileName").value("file2.txt"));
    }

    @GetMapping("/api/v1/file/downloadFile/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        Pair<ByteArrayResource, String> resourcePair = fileStorageService.loadFileAsResource(fileName);
        
        String mimeType = "application/octet-stream";
        if (fileName.endsWith(".txt")) {
            mimeType = "text/plain";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourcePair.getRight() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resourcePair.getLeft());
    }

}
