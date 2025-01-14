package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização do tipo de adesivo.
 * Este DTO encapsula as informações relacionadas ao tipo de adesivo.
 *
 * @param id          O ID único do tipo de adesivo.
 * @param description A descrição detalhada do tipo de adesivo.
 */
public record StickerTypeView(
        Long id,
        String description
) { }
