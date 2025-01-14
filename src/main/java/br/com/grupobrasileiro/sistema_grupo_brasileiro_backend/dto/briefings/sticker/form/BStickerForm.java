package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Adesivo (Sticker).
 * Este DTO encapsula as informações necessárias para o registro de um adesivo.
 *
 * @param idStickerType             O ID do tipo de adesivo, que é obrigatório.
 * @param idStickerInformationType  O ID do tipo de informação do adesivo, opcional.
 * @param sector                    O setor onde o adesivo será localizado, obrigatório e não pode estar vazio.
 * @param observations              Observações adicionais sobre o adesivo, opcional.
 */
public record BStickerForm(
        @NotNull(message = "The id of Sticker Type cannot be null")
        @JsonAlias({"id_sticker_type", "id-sticker-type"})
        Long idStickerType,

        @JsonAlias({"id_sticker_information_type", "id-sticker-information-type"})
        Long idStickerInformationType,

        @NotBlank(message = "The sector cannot be null or empty")
        String sector,

        String observations
) { }
