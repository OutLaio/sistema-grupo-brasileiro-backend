package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;

@Component
public class ProjectViewMapper implements Mapper<Project, ProjectView> {
	
	@Autowired
    private UserViewMapper userViewMapper;
	
    @Override
    public ProjectView map(Project i) {
    	Set<UserView> userViews = i.getUsers().stream()
                .map(userViewMapper::map)
                .collect(Collectors.toSet());
    	
        return new ProjectView(
            i.getId(),
            i.getTitle(),
			i.getDescription(),
			i.getDetails(),	
			i.getProgress(),
			i.getStatus(),
			userViews
        );
    }
}
