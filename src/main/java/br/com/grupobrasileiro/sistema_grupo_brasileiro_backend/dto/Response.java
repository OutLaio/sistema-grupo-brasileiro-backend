package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto;

import lombok.Data;

@Data
public class Response<T> {
    private String message;
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Response(String message) {
        this.message = message;
    }
}
