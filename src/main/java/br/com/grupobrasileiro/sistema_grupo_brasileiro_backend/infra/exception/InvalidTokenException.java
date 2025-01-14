package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException(String message) {
        super(message);
    }
}
