package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserConverter;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

@Component
public class ProjectFormMapper implements Mapper<ProjectForm, Project> {

    private final UserConverter userConverter;

    @Autowired
    public ProjectFormMapper(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public Project map(ProjectForm projectForm) {
        // Converte os objetos users no conjunto de ProjectUser
        Set<User> users = userConverter.converter(projectForm.users()); 
        Set<ProjectUser> projectUsers = users.stream()
            .map(userForm -> {
                User user = new User();
                user.setName(userForm.getName());
                user.setLastname(userForm.getLastname());
                user.setPhonenumber(userForm.getPhonenumber());
                user.setSector(userForm.getSector());
                user.setOccupation(userForm.getOccupation());
                user.setNop(userForm.getNop());
                user.setEmail(userForm.getEmail());
                user.setRole(userForm.getRole());

                ProjectUser projectUser = new ProjectUser();
                if (userForm.getRole() == RoleEnum.ROLE_COLLABORATOR.getCode()) {
	                projectUser.setCollaborator(user);
                }
                else if (userForm.getRole() == RoleEnum.ROLE_CLIENT.getCode()) {
	                projectUser.setClient(user);
				}
                
                return projectUser;
            })
            .collect(Collectors.toSet());

        return new Project(
            null,
            projectForm.title(),
            projectForm.description(),
            projectForm.progress(),
            projectForm.status(),
            projectUsers
        );
    }
}
