package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class EntityNotFoundException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
        super(message);
    }
}