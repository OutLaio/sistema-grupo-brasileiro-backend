package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import java.util.HashSet;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectForm(
        @NotBlank(message = "Title is required!") String title,

        @NotBlank(message = "Description is required!") String description,

        @NotNull(message = "Progress is required!") 
        @Min(value = 0, message = "Progress must be at least 0")
        @Max(value = 100, message = "Progress must be at most 100") 
        Integer progress,

        @NotBlank(message = "Status is required!") String status,
        
        Set<UserForm> users) {

    public ProjectForm {
        if (progress == null) {
            progress = 0;  
        }
        if (status == null) {
            status = ProjectStatusEnum.A_FAZER.getCode(); 
        }
        if (users == null) {
            users = new HashSet<>();
        }
    }
}