package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Material.
 * Este DTO encapsula as informações de um material para exibição.
 *
 * @param id            O id do material.
 * @param description   A descrição do material.
 */
public record MaterialView(
        Long id,
        String description
) {
}
