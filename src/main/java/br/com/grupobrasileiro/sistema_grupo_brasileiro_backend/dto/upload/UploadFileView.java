package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UploadFileView(

    @NotBlank(message = "O nome do arquivo não pode estar vazio")
    @Size(max = 255, message = "O nome do arquivo deve ter no máximo 255 caracteres")
    String fileName,

    @NotBlank(message = "A URI de download do arquivo não pode estar vazia")
    String fileDownloadUri,

    @NotBlank(message = "O tipo de arquivo não pode estar vazio")
    String fileType,

    @NotNull(message = "O tamanho do arquivo não pode ser nulo")
    Long size

) {
}
