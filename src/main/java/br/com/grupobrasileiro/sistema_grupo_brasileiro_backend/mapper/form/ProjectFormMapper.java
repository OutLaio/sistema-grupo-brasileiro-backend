package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectUserAdderForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {

    @Override
    public Project map(ProjectForm i) {
    	Set<User> users = ProjectUserAdderForm.converter(i.users());
    	
        return new Project(
            null,
            i.title(),
            i.description(),
            i.details(),
            i.progress(),
            i.status(),
            users
        );
    }
}