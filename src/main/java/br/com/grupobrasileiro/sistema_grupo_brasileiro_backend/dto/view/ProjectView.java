package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import java.util.Set;

public record ProjectView(
    Long id,
    String title,
    String description,
    Integer progress,
    String status,
    Set<UserView> users  
) {

}