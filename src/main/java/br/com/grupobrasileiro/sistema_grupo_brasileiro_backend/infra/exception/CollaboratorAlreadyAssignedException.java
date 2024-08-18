package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class CollaboratorAlreadyAssignedException extends RuntimeException {
    public CollaboratorAlreadyAssignedException(String s) {
        super(s);
    }
}