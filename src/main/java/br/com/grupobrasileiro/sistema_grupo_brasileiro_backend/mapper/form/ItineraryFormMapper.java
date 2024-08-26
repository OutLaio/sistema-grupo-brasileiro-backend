package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ItineraryForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Itinerary;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CompanyRepository;

@Component
public class ItineraryFormMapper {

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Itinerary map(ItineraryForm form) {
        BAgencyBoard bAgencyBoard = bAgencyBoardRepository.findById(form.bAgencyBoardId())
                .orElseThrow(() -> new RuntimeException("BAgencyBoard not found"));
        
            Company company = companyRepository.findByName(form.companyName());
            if(company == null){
                new EntityNotFoundException("Company not found");
            }

        return new Itinerary(
            null, 
            form.main(),
            form.connections(),
            bAgencyBoard,
            company
        );
    }
}
