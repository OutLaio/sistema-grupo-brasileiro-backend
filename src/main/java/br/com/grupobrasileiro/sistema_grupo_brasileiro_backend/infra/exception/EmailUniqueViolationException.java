package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class EmailUniqueViolationException extends RuntimeException {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailUniqueViolationException(String message) {
        super(message);
    }
}
