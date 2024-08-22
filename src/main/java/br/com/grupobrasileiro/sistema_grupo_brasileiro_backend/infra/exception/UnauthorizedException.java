package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class UnauthorizedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String s) {
        super(s);
    }
}