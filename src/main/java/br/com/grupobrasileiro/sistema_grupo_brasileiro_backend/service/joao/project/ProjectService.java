package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.joao.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.ProjectForm;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectFormMapper projectFormMapper;


    public Project register(ProjectForm projectForm) {
        Employee client = employeeRepository.findById(projectForm.idClient())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + projectForm.idClient()));
        Project project = projectFormMapper.map(projectForm);
        project.setClient(client);
        project = projectRepository.save(project);
        return project;
    }
}
