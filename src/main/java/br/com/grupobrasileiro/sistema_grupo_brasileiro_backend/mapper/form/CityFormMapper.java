package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CityForm;

@Component
public class CityFormMapper implements Mapper<CityForm, City>{
    @Override
    public City map(CityForm i) {
    	
    	return new City(
			null,
			i.name()
    	);
    }
}
