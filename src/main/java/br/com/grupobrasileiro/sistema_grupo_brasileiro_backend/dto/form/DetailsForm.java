package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DetailsForm(
		@NotBlank(message = "BoardLocation is required!")
		String boardLocation,

		@NotNull(message = "CompanySharing is required!")
	    Boolean companySharing,

	    @NotBlank(message = "BoardType is required!")
		@Size(max = 20, message = "BoardType must be less than 50 characters!") 
	    String boardType,

	    @NotBlank(message = "Material is required!")
	    String material,
	    
	    String observations,


        @NotNull(message = "Measurements are required!")
        List<@NotNull(message = "Measurement cannot be null!") MeasurementForm> measurements,


        @NotNull(message = "Itineraries are required!")
        List<@NotNull(message = "Itinerary cannot be null!") ItineraryForm> itineraries

    ) {
    }
