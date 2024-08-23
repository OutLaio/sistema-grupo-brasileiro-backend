package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class CollaboratorAlreadyAssignedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollaboratorAlreadyAssignedException(String s) {
        super(s);
    }
}