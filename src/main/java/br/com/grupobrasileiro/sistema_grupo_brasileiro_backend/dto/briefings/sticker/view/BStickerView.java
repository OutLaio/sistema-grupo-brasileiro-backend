package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Adesivo (Sticker).
 * Este DTO encapsula as informações de visualização de um adesivo registrado no sistema.
 *
 * @param id                     O ID único do adesivo.
 * @param stickerType            O tipo de adesivo associado.
 * @param stickerInformationType O tipo de informação associada ao adesivo (opcional).
 * @param sector                 O setor onde o adesivo será utilizado.
 * @param observations           Observações adicionais sobre o adesivo.
 */
public record BStickerView(
        Long id,
        StickerTypeView stickerType,
        StickerInformationTypeView stickerInformationType,
        String sector,
        String observations
) { }
