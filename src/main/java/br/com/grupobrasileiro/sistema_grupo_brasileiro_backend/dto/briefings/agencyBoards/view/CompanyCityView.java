package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

public record CompanyCityView(
        Long id,
        CityView city,
        CompanyView company
) {
}
