package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

public class MyFileNotFoundException extends RuntimeException{
    public MyFileNotFoundException(String message){
        super(message);
    }
}
