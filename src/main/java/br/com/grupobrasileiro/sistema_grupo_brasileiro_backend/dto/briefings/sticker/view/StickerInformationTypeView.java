package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização do tipo de informação de adesivo.
 * Este DTO encapsula as informações relacionadas ao tipo de dado de adesivo.
 *
 * @param id          O ID único do tipo de informação do adesivo.
 * @param description A descrição detalhada do tipo de informação do adesivo.
 */
public record StickerInformationTypeView(
        Long id,
        String description
) { }
