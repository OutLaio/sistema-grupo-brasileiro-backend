package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class InvalidRoleException extends RuntimeException{
	
	public InvalidRoleException(String message) {
		super(message);
	}
}
