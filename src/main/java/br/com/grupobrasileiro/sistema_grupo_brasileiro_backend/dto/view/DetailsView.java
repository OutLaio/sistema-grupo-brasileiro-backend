package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import java.util.List;

public record DetailsView(    
		String boardLocation,
	    Boolean companySharing,
	    String boardType,
	    String material,
	    String observations,
	    List<MeasurementView> measurements,
	    List<ItineraryView> itineraries) {

}
