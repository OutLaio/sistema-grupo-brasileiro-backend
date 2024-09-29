package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidProfileException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectFormMapper projectFormMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Autowired
    private BAgencyBoardDetailedViewMapper bAgencyBoardDetailedViewMapper;

    @Autowired
    private BSignpostDetailedViewMapper bSignpostDetailedViewMapper;

    @Autowired
    private UserRepository userRepository;


    public Project register(ProjectForm projectForm) {
        Employee client = employeeRepository.findById(projectForm.idClient())
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + projectForm.idClient()));
        Project project = projectFormMapper.map(projectForm);
        project.setClient(client);
        project = projectRepository.save(project);
        return project;
    }

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

    public Set<ProjectView> getAll(Long idUser) {
        User user = userRepository.getReferenceById(idUser);
        Set<ProjectView> projects;
        if(user.getProfile().getId().equals(3L)){
            Hibernate.initialize(user.getEmployee().getOwnedProjects());
            projects = user.getEmployee().getOwnedProjects().stream().map(
                project -> projectViewMapper.map(project)
            ).collect(Collectors.toSet());
        } else {
            projects = projectRepository.findAll().stream().map(
                project -> projectViewMapper.map(project)
            ).collect(Collectors.toSet());
        }
        return projects;
    }

    public ResponseEntity<?> getById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found for id: " + id)
        );
        String briefingType = project.getBriefing().getBriefingType().getDescription();
        if (briefingType.equals("PLACA DE ITINERÁRIOS")){
            BAgencyBoard bAgencyBoard = project.getBriefing().getAgencyBoard();
            if (bAgencyBoard == null) throw new NullPointerException("Error retrieving the briefing: The field agencyBoard is null");
            BAgencyBoardDetailedView view = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);
            return ResponseEntity.ok(view);
        }
        if (briefingType.equals("PLACA DE SINALIZAÇÃO INTERNA")){
            BSignpost bSignpost = project.getBriefing().getSignpost();
            if (bSignpost == null) throw new NullPointerException("Error retrieving the briefing: The field signpost is null");
            BSignpostDetailedView view = bSignpostDetailedViewMapper.map(bSignpost);
            return ResponseEntity.ok(view);
        }
        if (briefingType.equals("ADESIVOS")){
            //TODO: Implement the search for the briefing Stickers
        }
        if (briefingType.equals("IMPRESSOS")){
            //TODO: Implement the search for the briefing Printeds
        }
        if (briefingType.equals("LAYOUTS PARA BRINDES")){
            //TODO: Implement the search for the briefing Gifts
        }
        if (briefingType.equals("CAMPANHAS INTERNAS")){
            //TODO: Implement the search for the briefing Internal Campaigns
        }
        if (briefingType.equals("COMUNICADOS")){
            //TODO: Implement the search for the briefing Handouts
        }
        throw new IllegalArgumentException("Error retrieving the briefing: The project briefing type is not valid");
    }
}
