package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(String message) {
        super(message);
    }
}