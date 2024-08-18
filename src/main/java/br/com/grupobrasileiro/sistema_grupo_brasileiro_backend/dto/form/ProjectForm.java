package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectForm(
        @NotBlank(message = "O título é obrigatório!") String title,

        @NotBlank(message = "A descrição é obrigatória!") String description,

        @Min(value = 0, message = "O progresso deve ser no mínimo 0")
        @Max(value = 100, message = "O progresso deve ser no máximo 100") 
        Integer progress,

        @NotBlank(message = "O status é obrigatório!") String status,
        
        @NotNull(message = "O ID do cliente é obrigatório!") Long clientId) {

    public ProjectForm {
        if (progress == null) {
            progress = 0; 
        }
        if (status == null) {
            status = ProjectStatusEnum.A_FAZER.getCode();
        }
    }
}
