package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.CalendarTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;

@Component
public class CalendarTypeViewMapper implements Mapper<CalendarType, CalendarTypeView> {

    @Override
    public CalendarTypeView map(CalendarType calendarType) {
        return new CalendarTypeView(
                calendarType.getId(),
                calendarType.getDescription()
        );
    }
}
