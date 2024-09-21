package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message){
        super(message);
    }
}
