package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class InvalidTokenException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String message) {
        super(message);
    }
}
