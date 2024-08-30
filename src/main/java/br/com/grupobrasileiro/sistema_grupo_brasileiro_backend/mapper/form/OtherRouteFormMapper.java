package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.OtherRouteForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;

@Component
public class OtherRouteFormMapper implements Mapper<OtherRouteForm, OtherRoute>{
	
	private final BAgencyBoardRepository bAgencyBoardRepository;
	
	public OtherRouteFormMapper(BAgencyBoardRepository bAgencyBoardRepository) {
		this.bAgencyBoardRepository = bAgencyBoardRepository;
	}
	
	@Override
    public OtherRoute map(OtherRouteForm i) {
		BAgencyBoard bAgencyBoard = bAgencyBoardRepository.findById(i.bAgencyBoardId())
				.orElseThrow(() -> new EntityNotFoundException("bAgencyBoard não encontrado com o ID: "+ i.bAgencyBoardId()));
		
		return new OtherRoute(
				null,
				bAgencyBoard,
				i.city(),
				i.company(),
				i.type()
				);
	}
}
