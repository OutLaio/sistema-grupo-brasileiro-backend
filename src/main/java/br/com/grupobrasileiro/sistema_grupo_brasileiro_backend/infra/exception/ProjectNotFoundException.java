package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String s) {
        super(s);
    }
}