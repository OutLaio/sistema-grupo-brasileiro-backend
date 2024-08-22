package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class CompanyAlreadyExistsException extends RuntimeException {

    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
