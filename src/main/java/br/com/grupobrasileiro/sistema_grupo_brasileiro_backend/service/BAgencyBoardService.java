package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateBAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.BAgencyBoardFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.BAgencyBoardViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;

@Service
public class BAgencyBoardService {

	@Autowired
	private BAgencyBoardRepository bAgencyBoardRepository;

	@Autowired
	private BAgencyBoardFormMapper bAgencyBoardFormMapper;

	@Autowired
	private BAgencyBoardViewMapper bAgencyBoardViewMapper;

	@Transactional
    public BAgencyBoardView save(BAgencyBoardForm bAgencyBoardForm) {
        BAgencyBoard bAgencyBoard = bAgencyBoardFormMapper.map(bAgencyBoardForm);

        // Salva o projeto no repositório
        bAgencyBoardRepository.save(bAgencyBoard);
        return bAgencyBoardViewMapper.map(bAgencyBoard);
    }

	@Transactional(readOnly = true)
	public BAgencyBoardView getBAgencyBoardById(Long id) {
		BAgencyBoard bAgencyBoard = bAgencyBoardRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("BAgencyBoard not found with id: " + id));
		return bAgencyBoardViewMapper.map(bAgencyBoard);
	}

	@Transactional
	public BAgencyBoardView updateBAgencyBoard(Long id, UpdateBAgencyBoardForm form) {
		BAgencyBoard bAgencyBoard = bAgencyBoardRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("BAgencyBoard not found with id: " + id));
		
		// Atualizar os campos somente se houver mudanças
	    if (!Objects.equals(bAgencyBoard.getBoardLocation(), form.boardLocation())) {
	        bAgencyBoard.setBoardLocation(form.boardLocation());
	    }
	    
	    if (!Objects.equals(bAgencyBoard.getCompanySharing(), form.companySharing())) {
	        bAgencyBoard.setCompanySharing(form.companySharing());
	    }
	    
	    if (!Objects.equals(bAgencyBoard.getBoardType(), form.boardType())) {
	        bAgencyBoard.setBoardType(form.boardType());
	    }
	    
	    if (!Objects.equals(bAgencyBoard.getMaterial(), form.material())) {
	        bAgencyBoard.setMaterial(form.material());
	    }
	    
	    if (!Objects.equals(bAgencyBoard.getObservations(), form.observations())) {
	        bAgencyBoard.setObservations(form.observations());
	    }
		
		BAgencyBoard updatedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);
		return bAgencyBoardViewMapper.map(updatedBAgencyBoard);
	}

	@Transactional
	public Page<BAgencyBoardView> bAgencyBoardAll(PageRequest pageRequest) {
		Page<BAgencyBoard> bAgencyBoardPage = bAgencyBoardRepository.findAll(pageRequest);
		return bAgencyBoardPage.map(bAgencyBoardViewMapper::map);
	}

}
