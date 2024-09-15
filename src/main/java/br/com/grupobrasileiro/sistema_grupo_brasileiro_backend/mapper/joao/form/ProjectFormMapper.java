package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {



    @Override
    public Project map(ProjectForm projectForm) {
        return new Project(
                null,
                null,
                null,
                projectForm.title(),
                null,
                false,
                null
        );    }
}
