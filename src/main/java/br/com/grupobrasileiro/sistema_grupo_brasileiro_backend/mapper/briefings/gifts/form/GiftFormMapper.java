package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.CalendarTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.GiftTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingShirtTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.StampRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;

@Component
public class GiftFormMapper implements Mapper<GiftForm, BGift> {

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

    @Override
    public BGift map(GiftForm form) {
        return new BGift(
            null,
            null,
            giftTypeRepository.findById(form.idGiftType()).orElseThrow(
                () -> new EntityNotFoundException("Gift Type not found with id: " + form.idGiftType())
            ),
            form.idPrintingType() == null? null : printingTypeRepository.findById(form.idPrintingType()).orElseThrow(
                () -> new EntityNotFoundException("Printing Type not found with id: " + form.idPrintingType())
            ),
            form.idPrintingShirtType() == null? null : printingShirtTypeRepository.findById(form.idPrintingShirtType()).orElseThrow(
                () -> new EntityNotFoundException("Printing Shirt Type not found with id: " + form.idPrintingShirtType())
            ),
            form.idStamp() == null? null : stampRepository.findById(form.idStamp()).orElseThrow(
                () -> new EntityNotFoundException("Stamp not found with id: " + form.idStamp())
            ),
            form.idCalendarType() == null? null : calendarTypeRepository.findById(form.idCalendarType()).orElseThrow(
                () -> new EntityNotFoundException("Calendar Type not found with id: " + form.idCalendarType())
            ),
            form.giftModel(),
            form.linkModel()
        );
    }
}
