package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class UserIsNotActiveException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserIsNotActiveException(String s) {
        super(s);
    }
}