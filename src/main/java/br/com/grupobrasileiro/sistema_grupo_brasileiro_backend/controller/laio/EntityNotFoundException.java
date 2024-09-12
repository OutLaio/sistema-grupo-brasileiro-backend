package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.laio;

public class EntityNotFoundException extends RuntimeException {
    EntityNotFoundException(String message){
        super(message);
    }
}
