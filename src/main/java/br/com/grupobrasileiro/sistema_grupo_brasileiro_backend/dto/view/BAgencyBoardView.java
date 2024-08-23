package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;


public record BAgencyBoardView(
		Long id,
		String boardLocation,
		Boolean companySharing,
		String boardType,
		String material,
		String observations,
		Long projectId
) {

}