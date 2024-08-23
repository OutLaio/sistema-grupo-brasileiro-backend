package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ItineraryView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Itinerary;

@Component
public class ItineraryViewMapper {

    public ItineraryView map(Itinerary itinerary) {
        return new ItineraryView(
            itinerary.getId(),
            itinerary.getMain(),
            itinerary.getConnections(),
            itinerary.getBAgencyBoard().getId(),
            itinerary.getCompany().getId()
        );
    }
}
