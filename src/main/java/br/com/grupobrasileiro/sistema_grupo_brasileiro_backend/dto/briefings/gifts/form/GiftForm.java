package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record GiftForm(

        @NotNull(message = "The id_gift_type cannot be null")
        @JsonAlias({"id_gift_type"})
        Long idGiftType,

        @JsonAlias({"id_printing_type"})
        Long idPrintingType,

        @JsonAlias({"id_printing_shirt_type"})
        Long idPrintingShirtType,

        @JsonAlias({"id_stamp"})
        Long idStamp,

        @JsonAlias({"id_calendar_type"})
        Long idCalendarType,

        @JsonAlias({"gift_model"})
        String giftModel,

        @JsonAlias({"link_model"})
        String linkModel
) {
}
