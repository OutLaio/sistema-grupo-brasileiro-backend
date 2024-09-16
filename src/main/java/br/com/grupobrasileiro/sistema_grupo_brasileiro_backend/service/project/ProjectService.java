package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidProfileException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;

    public void assignCollaborator(Long id, AssignCollaboratorForm form) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project nof found for id: " + id)
        );

        Employee employee = employeeRepository.findById(form.idCollaborator()).orElseThrow(
                () -> new EntityNotFoundException("Employee not found for id: " + form.idCollaborator())
        );

        if(!employee.getUser().getProfile().getId().equals(2L))
            throw new InvalidProfileException("The employee is not a Collaborator");

        if(project.getStatus().equals(ProjectStatusEnum.TO_DO.toString()))
            project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());

        project.setCollaborator(employee);
        projectRepository.save(project);
    }

    public void setHasConfection(Long id, boolean hasConfection) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found for id: " + id)
        );
        if(hasConfection)
            project.setStatus(ProjectStatusEnum.IN_PRODUCTION.toString());
        else
            project.setStatus(ProjectStatusEnum.COMPLETED.toString());
        projectRepository.save(project);
    }

    public void setFinished(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found for id: " + id)
        );
        project.setStatus(ProjectStatusEnum.COMPLETED.toString());
        projectRepository.save(project);
    }

    public void setStandby(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found for id: " + id)
        );
        project.setStatus(ProjectStatusEnum.STAND_BY.toString());
        projectRepository.save(project);
    }
}
