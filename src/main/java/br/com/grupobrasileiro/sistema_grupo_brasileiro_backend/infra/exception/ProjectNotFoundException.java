package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class ProjectNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProjectNotFoundException(String s) {
        super(s);
    }
}