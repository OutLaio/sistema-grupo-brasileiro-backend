package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException(String s) {
        super(s);
    }
}