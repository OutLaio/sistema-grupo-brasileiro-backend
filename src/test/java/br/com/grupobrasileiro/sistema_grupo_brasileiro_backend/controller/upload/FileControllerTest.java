package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.upload;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload.FileStorageService;

import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.flyway.enabled=false")
public class FileControllerTest{

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

        // Configurar mocks para o serviço de armazenamento de arquivos
        when(fileStorageService.storeFile(file1)).thenReturn("file1.txt");
        when(fileStorageService.storeFile(file2)).thenReturn("file2.txt");

        // Executar a requisição e verificar os resultados
        mockMvc.perform(multipart("/api/v1/file/uploadMultipleFiles")
                .file(file1)
                .file(file2))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
