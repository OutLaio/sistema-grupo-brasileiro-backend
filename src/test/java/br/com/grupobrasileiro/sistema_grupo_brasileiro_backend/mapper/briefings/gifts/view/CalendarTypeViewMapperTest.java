package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.CalendarTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarTypeViewMapperTest {

    private final CalendarTypeViewMapper calendarTypeViewMapper = new CalendarTypeViewMapper();

    @Test
    public void testMap() {
        CalendarType calendarType = new CalendarType();
        calendarType.setId(1L);
        calendarType.setDescription("Descrição do Tipo de Calendário");

        CalendarTypeView result = calendarTypeViewMapper.map(calendarType);

        
        assertEquals(1L, result.id(), "O ID deve ser 1");
        assertEquals("Descrição do Tipo de Calendário", result.description(), "A descrição deve ser 'Descrição do Tipo de Calendário'");
    }
}