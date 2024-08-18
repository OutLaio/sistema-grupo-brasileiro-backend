package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.CollaboratorAlreadyAssignedException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.ProjectNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectUserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectUserRepository projectUserRepository;

	@Autowired
	private ProjectFormMapper projectFormMapper;

	@Autowired
	private ProjectViewMapper projectViewMapper;

	@Autowired
	private UserRepository userRepository;

	@Transactional
    public void save(ProjectForm projectForm) {
        Project project = projectFormMapper.map(projectForm);

        // Salva o projeto no repositório
        Project savedProject = projectRepository.save(project);
        
        // Salva as associações de ProjectUser
        for (ProjectUser projectUser : savedProject.getUsers()) {
            projectUser.setProject(savedProject);
        }

        // Converte o Project salvo para ProjectView
//        return projectViewMapper.map(savedProject);
    }

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

	@Transactional
	public Page<ProjectView> projectAll(PageRequest pageRequest) {
		Page<Project> projectPage = projectRepository.findAll(pageRequest);
		return projectPage.map(projectViewMapper::map);
	}
	
	@Transactional
	public void assignCollaboratorToProject(Long projectId, Long collaboratorId) {
	    // Verifica se o projeto existe
	    Project project = projectRepository.findById(projectId)
	        .orElseThrow(() -> new ProjectNotFoundException("Projeto não encontrado."));

	    // Verifica se o colaborador existe
	    User collaborator = userRepository.findById(collaboratorId)
	        .orElseThrow(() -> new RuntimeException("Colaborador não encontrado."));

	    // Verifica se há algum cliente associado ao projeto
	    ProjectUser projectUser = projectUserRepository.findByProjectAndClientIsNotNull(project)
	        .orElseThrow(() -> new RuntimeException("Nenhum cliente associado a este projeto para adicionar um colaborador."));

	    // Verifica se o colaborador já está associado ao projeto
	    boolean exists = projectUserRepository.existsByProjectAndCollaborator(project, collaborator);
	    if (exists) {
	        throw new CollaboratorAlreadyAssignedException("O colaborador já está atribuído a este projeto.");
	    }

	    // Atribui o colaborador ao projeto
	    projectUser.setCollaborator(collaborator);
	    projectUserRepository.save(projectUser);
	}

}
