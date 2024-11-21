package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.upload;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload.UploadFileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.tuple.Pair;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {


    @Autowired
    private FileStorageService fileStorageService;

    @Operation(summary = "Fazer upload de um arquivo",
               description = "Faz o upload de um arquivo e retorna informações sobre ele.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo carregado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "500", description = "Erro ao carregar arquivo")
    })
    @PostMapping("/uploadFile")
    public UploadFileView uploadFile(@Parameter(description = "Arquivo a ser carregado", required = true)
                                      @RequestParam("file") MultipartFile file) {
        logger.info("Recebendo arquivo para upload: {}", file.getOriginalFilename());

        String fileName;
        try {
            fileName = fileStorageService.storeFile(file);
            logger.info("Arquivo '{}' carregado com sucesso.", fileName);
        } catch (Exception e) {
            logger.error("Erro ao carregar o arquivo '{}': {}", file.getOriginalFilename(), e.getMessage());
            throw e;  // ou retorne uma resposta adequada para erro
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/file/downloadFile/")
                .path(fileName)
                .toUriString();

        logger.debug("Arquivo disponível em: {}", fileDownloadUri);
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
        logger.info("Recebendo múltiplos arquivos para upload.");

        return Arrays.stream(files)
                .map(this::uploadFile)
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
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Pair<ByteArrayResource, String> fileData = fileStorageService.loadFileAsResource(fileName);
        ByteArrayResource resource = fileData.getLeft();
        String fileName_ = fileData.getRight();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileName_))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
