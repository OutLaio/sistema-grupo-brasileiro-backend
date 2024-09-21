package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import java.util.List;

public record RouteView(
        Long id,
        CompanyView company,
        List<CityView> cities,
        String type
) {

	public RouteView(Long routerViewId, CompanyCityView companyCityView, String routerViewType) {

		this(
		        routerViewId,
		        companyCityView.company() != null ? companyCityView.company() : null,
		        companyCityView.city() != null ? List.of(companyCityView.city()) : null,
		        routerViewType
		);
	}
}
