package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper;


import org.springframework.stereotype.Component;

@Component
public interface Mapper<Input, Output> {
	public Output map(Input i);

}
