package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;

@Component
public class ProjectViewMapper implements Mapper<Project, ProjectView> {

    @Override
    public ProjectView map(Project project) {
        // Converte o conjunto de ProjectUser para UserView
        // Set<UserView> userViews = project.getUsers().stream()
        //     .flatMap(projectUser -> {
        //         // Mapear tanto o cliente quanto o colaborador para UserView
        //         UserView clientView = new UserView(
        //             projectUser.getClient().getId(),
        //             projectUser.getClient().getName(),
        //             projectUser.getClient().getLastname(),
        //             projectUser.getClient().getPhonenumber(),
		// 			projectUser.getClient().getSector(),
		// 			projectUser.getClient().getOccupation(),
		// 			projectUser.getClient().getNop(),
        //             projectUser.getClient().getEmail(),
        //             projectUser.getClient().getRole(),
		// 			projectUser.getClient().getActive()
        //         );

        //         UserView collaboratorView = new UserView(
        //         		projectUser.getCollaborator().getId(),
        //                 projectUser.getCollaborator().getName(),
        //                 projectUser.getCollaborator().getLastname(),
        //                 projectUser.getCollaborator().getPhonenumber(),
    	// 				projectUser.getCollaborator().getSector(),
    	// 				projectUser.getCollaborator().getOccupation(),
    	// 				projectUser.getCollaborator().getNop(),
        //                 projectUser.getCollaborator().getEmail(),
        //                 projectUser.getCollaborator().getRole(),
    	// 				projectUser.getCollaborator().getActive()
        //         );

        //         return Set.of(clientView, collaboratorView).stream();
        //     })
        //     .collect(Collectors.toSet());

        // Cria e retorna o ProjectView
        return new ProjectView(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getProgress(),
            project.getStatus()
        );
    }
}



