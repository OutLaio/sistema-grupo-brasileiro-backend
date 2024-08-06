package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class EmailUniqueViolationException extends RuntimeException {

	
    public EmailUniqueViolationException(String message) {
        super(message);
    }
}
