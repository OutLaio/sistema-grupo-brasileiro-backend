package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;


import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyCityForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.CompanyCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CityRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CompanyRepository;

@Component
public class CompanyCityFormMapper implements Mapper<CompanyCityForm, CompanyCity>{
	
	private final CityRepository cityRepository;
	private final CompanyRepository companyRepository;
	
	public CompanyCityFormMapper(CityRepository cityRepository, CompanyRepository companyRepository) {
		this.cityRepository = cityRepository;
		this.companyRepository = companyRepository;
	}
	
	@Override
    public CompanyCity map(CompanyCityForm i) {
    	City city = cityRepository.findById(i.cityId())
    			.orElseThrow(() -> new EntityNotFoundException("Cidade não encontrado com o ID: " +i.cityId() ));
    	
    	Company company = companyRepository.findById(i.companyId())
    			.orElseThrow(() -> new EntityNotFoundException("Empresa não encontrado com o ID: "+ i.companyId() ));
    	
    	return new CompanyCity(
			null,
			city,
			company
    	);
    }
	

}
