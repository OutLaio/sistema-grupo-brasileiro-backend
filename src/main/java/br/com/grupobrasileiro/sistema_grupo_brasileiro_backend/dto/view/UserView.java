package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

public record UserView(
	Long id,
	String name,
	String lastname,
	String phonenumber,
	String sector,
	String occupation,
	String nop,
	String email,
	Integer role	
) {

}
