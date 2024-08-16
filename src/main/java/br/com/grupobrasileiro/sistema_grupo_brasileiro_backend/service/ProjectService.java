package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidRoleException;
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
	public ProjectView save(ProjectForm dto) {
	    // Mapeia o DTO para a entidade Project sem usuários associados
	    Project project = projectFormMapper.map(dto);

	    // Salva a entidade no banco de dados
	    projectRepository.save(project);

		for (Integer userId : dto.users()) {
			Long userIdLong = userId.longValue();
			Optional<User> optionalUser = userRepository.findById(userIdLong);

			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				ProjectUser projectUser = new ProjectUser();

				if (user.getRole() == RoleEnum.ROLE_CLIENT.getCode()) {
					projectUser.setProject(project);
					projectUser.setClient(user);
					projectUser.setCollaborator(null);
				}
				else {
					throw new InvalidRoleException("Usuário com Função Inválida para o Projeto.");
				}
					projectUserRepository.save(projectUser);
				
			} else {
				throw new EntityNotFoundException("Usuário com ID " + userId + " não encontrado.");
			}
		}
	    // Retorna a visão do projeto salvo
	    return projectViewMapper.map(project);
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

}
