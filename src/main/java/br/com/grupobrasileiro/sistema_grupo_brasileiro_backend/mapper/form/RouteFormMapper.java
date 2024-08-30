package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.RouteForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.CompanyCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CompanyCityRepository;

@Component
public class RouteFormMapper implements Mapper<RouteForm, Route>{
	
	private final BAgencyBoardRepository bAgencyBoardRepository;
	private final CompanyCityRepository companyCityRepository;
	
	public RouteFormMapper(BAgencyBoardRepository bAgencyBoardRepository, CompanyCityRepository companyCityRepository) {
		this.bAgencyBoardRepository = bAgencyBoardRepository;
		this.companyCityRepository = companyCityRepository;
	}
	
	@Override
    public Route map(RouteForm i) {
		BAgencyBoard bAgencyBoard = bAgencyBoardRepository.findById(i.bAgencyBoardId())
				.orElseThrow(() -> new EntityNotFoundException("bAgencyBoard não encontrado com o ID: "+ i.bAgencyBoardId()));
		
		CompanyCity companyCity = companyCityRepository.findById(i.cityCompanyId())
				.orElseThrow(() -> new EntityNotFoundException("CompanyCity não encontrado com o ID: "+ i.cityCompanyId()));
		
		return new Route(
			null,
			bAgencyBoard,
			companyCity,
			i.type()
		);
	}
}
