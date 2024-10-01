package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message){
        super(message);
    }
}
