package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class InvalidRoleException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRoleException(String message) {
		super(message);
	}
}
