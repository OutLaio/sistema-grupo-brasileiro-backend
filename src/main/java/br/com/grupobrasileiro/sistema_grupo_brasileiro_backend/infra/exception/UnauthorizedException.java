package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String s) {
        super(s);
    }
}