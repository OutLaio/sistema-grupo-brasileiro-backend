package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;


public record BGiftView(
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
