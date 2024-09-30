package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload.UploadFileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Operation(summary = "Fazer upload de um arquivo",
               description = "Faz o upload de um arquivo e retorna informações sobre ele.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo carregado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "500", description = "Erro ao carregar arquivo")
    })
    @PostMapping("/uploadFile")
    public UploadFileView uploadFile(@Parameter(description = "Arquivo a ser carregado", required = true)
                                      @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/file/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileView(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Operation(summary = "Fazer upload de múltiplos arquivos",
               description = "Faz o upload de vários arquivos e retorna informações sobre cada um deles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivos carregados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "500", description = "Erro ao carregar arquivos")
    })
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileView> uploadMultipleFiles(@Parameter(description = "Arquivos a serem carregados", required = true)
                                                     @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Baixar um arquivo",
               description = "Baixa um arquivo pelo nome especificado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo baixado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro ao baixar arquivo")
    })
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@Parameter(description = "Nome do arquivo a ser baixado", required = true)
                                                 @PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Não foi possível determinar o tipo de arquivo!");
        }

        if (contentType == null) {
            logger.info("Não foi possível determinar o tipo de arquivo!");
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
