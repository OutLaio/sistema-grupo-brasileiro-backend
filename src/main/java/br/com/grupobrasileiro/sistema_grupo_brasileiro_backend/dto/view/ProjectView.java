package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;


public record ProjectView(
    Long id,
    String title,
    String description,
    Integer progress,
    String status,
    Long projectUserId
) {

}