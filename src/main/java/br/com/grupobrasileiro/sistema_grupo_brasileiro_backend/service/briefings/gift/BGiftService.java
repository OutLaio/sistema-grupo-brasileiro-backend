package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form.GiftFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.BGiftDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.BGiftRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.CalendarTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.GiftTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingShirtTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.StampRepository;

@Service
public class BGiftService {

    @Autowired
    private BGiftRepository giftRepository;

    @Autowired
    private GiftTypeRepository giftTypeRepository;

    @Autowired
    private PrintingTypeRepository printingTypeRepository;

    @Autowired
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @Autowired
    private StampRepository stampRepository;

    @Autowired
    private CalendarTypeRepository calendarTypeRepository;

    @Autowired
    private GiftFormMapper GiftFormMapper;

    @Autowired
    private BGiftDetailedViewMapper bGiftDetailedViewMapper;

    public BGiftDetailedView register(GiftForm GiftForm, Briefing briefing) {

        GiftType giftType = giftTypeRepository.getReferenceById(GiftForm.idGiftType());
        PrintingType printingType = GiftForm.idPrintingType() != null ? printingTypeRepository.getReferenceById(GiftForm.idPrintingType()) : null;
        PrintingShirtType printingShirtType = GiftForm.idPrintingShirtType() != null ? printingShirtTypeRepository.getReferenceById(GiftForm.idPrintingShirtType()) : null;
        Stamp stamp = GiftForm.idStamp() != null ? stampRepository.getReferenceById(GiftForm.idStamp()) : null;
        CalendarType calendarType = GiftForm.idCalendarType() != null ? calendarTypeRepository.getReferenceById(GiftForm.idCalendarType()) : null;

        BGift bGift = GiftFormMapper.map(GiftForm);
        bGift.setBriefing(briefing);
        bGift.setGiftType(giftType);
        bGift.setPrintingType(printingType);
        bGift.setPrintingShirtType(printingShirtType);
        bGift.setStamp(stamp);
        bGift.setCalendarType(calendarType);

        bGift = giftRepository.save(bGift);

        return bGiftDetailedViewMapper.map(bGift);
    }
}

