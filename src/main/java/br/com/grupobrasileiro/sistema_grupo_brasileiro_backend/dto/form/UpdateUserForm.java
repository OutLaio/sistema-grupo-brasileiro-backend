package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserForm(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    String name,

    @NotBlank(message = "Lastname is required")
    @Size(max = 255, message = "Lastname cannot exceed 255 characters")
    String lastname,

    @NotBlank(message = "Phonenumber is required")
    @Size(max = 255, message = "Phonenumber cannot exceed 255 characters")
    String phonenumber,

    @NotBlank(message = "Occupation is required")
    @Size(max = 255, message = "Occupation cannot exceed 255 characters")
    String occupation,

    @NotBlank(message = "NOP is required")
    @Size(max = 255, message = "NOP cannot exceed 255 characters")
    String nop,

    @NotBlank(message = "Sector is required")
    @Size(max = 255, message = "Sector cannot exceed 255 characters")
    String sector
) {}
