package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

public record UserView(
	Long id,
	String name,
	String lastname,
	String phonenumber,
	String sector,
	String occupation,
	String nop,
	String email,
	RoleEnum role	
) {

}
