package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectFormMapper projectFormMapper;
	
	@Autowired
	private ProjectViewMapper projectViewMapper;

	@Transactional(readOnly = true)
	public ProjectView getProjectById(Long id) {
		Project project = projectRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
		return projectViewMapper.map(project);
	}

	@Transactional
	public ProjectView updateProjectStatus(Long id, String newStatus) {
		Project project = projectRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
		
		// Validar status
		if (!isValidStatus(newStatus)) {
			throw new IllegalArgumentException("Invalid status value: " + newStatus);
		}
		
		// Atualizar status e salvar
		project.setStatus(newStatus);
		Project updatedProject = projectRepository.save(project);
		return projectViewMapper.map(updatedProject);
	}

	private boolean isValidStatus(String status) {
		return status.equals("AF") || status.equals("EA") || status.equals("AA")
			|| status.equals("AP") || status.equals("EC") || status.equals("CO");
	}
}
