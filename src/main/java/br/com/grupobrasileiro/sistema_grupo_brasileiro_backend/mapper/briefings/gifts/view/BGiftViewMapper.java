package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;

@Component
public class BGiftViewMapper implements Mapper<BGift, BGiftView>{

    @Autowired
    private GiftTypeViewMapper giftTypeViewMapper;

    @Autowired
    private PrintingTypeViewMapper printingTypeViewMapper;

    @Autowired
    private PrintingShirtTypeViewMapper printingShirtTypeViewMapper;

    @Autowired
    private StampViewMapper stampViewMapper;

    @Autowired
    private CalendarTypeViewMapper calendarTypeViewMapper;

    @Override
        public BGiftView map(BGift bGift) {
            return new BGiftView(
                    bGift.getId(),
                    giftTypeViewMapper.map(bGift.getGiftType()),
                    printingTypeViewMapper.map(bGift.getPrintingType()),
                    printingShirtTypeViewMapper.map(bGift.getPrintingShirtType()),
                    stampViewMapper.map(bGift.getStamp()),
                    calendarTypeViewMapper.map(bGift.getCalendarType()),
                    bGift.getGiftModel(),
                    bGift.getLinkModel() 
            );
        }
}
