package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view;

public record VersionView(
        Long id,
        Integer versionNumber,
        String productLink,
        Boolean clientApprove,
        Boolean supervisorApprove,
        String feedback
) {
}
