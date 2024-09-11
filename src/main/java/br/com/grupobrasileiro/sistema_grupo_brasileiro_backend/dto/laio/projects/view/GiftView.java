package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.gifts.view.CalendarTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.gifts.view.GiftTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.gifts.view.PrintingShirtTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.gifts.view.StampView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.printeds.view.PrintingTypeView;

public record GiftView(
        Long id,
        GiftTypeView giftType,
        PrintingTypeView printingType,
        PrintingShirtTypeView printingShirtType,
        StampView stamp,
        CalendarTypeView calendarType,
        String giftModel,
        String linkModel
) {
}
