package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.BPrintedsDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.BStickerDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.CollaboratorAlreadyAssignedException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidProfileException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.BGiftDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.BInternalCampaignDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view.BPrintedsDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.view.BStickerDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BriefingRepository briefingRepository;

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
    private BStickerDetailedViewMapper bStickerDetailedViewMapper;

    @Autowired
    private BPrintedsDetailedViewMapper bPrintedsDetailedViewMapper;

    @Autowired
    private BGiftDetailedViewMapper bGiftDetailedViewMapper;

    @Autowired
    private BInternalCampaignDetailedViewMapper bInternalCampaignDetailedViewMapper;

    @Autowired
    private BHandoutDetailedViewMapper bHandoutDetailedViewMapper;

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

        if(project.getCollaborator().equals(employee))
            throw new CollaboratorAlreadyAssignedException("Collaborator is already assigned to the project");

        project.setCollaborator(employee);

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
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        if (briefingType.equals("PLACA DE SINALIZAÇÃO INTERNA")){
            BSignpost bSignpost = project.getBriefing().getSignpost();
            if (bSignpost == null) throw new NullPointerException("Error retrieving the briefing: The field signpost is null");
            BSignpostDetailedView view = bSignpostDetailedViewMapper.map(bSignpost);
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        if (briefingType.equals("ADESIVOS")){
            BSticker bSticker = project.getBriefing().getSticker();
            if (bSticker == null) throw new NullPointerException("Error retrieving the briefing: The field sticker is null");
            BStickerDetailedView view = bStickerDetailedViewMapper.map(bSticker);
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        if (briefingType.equals("IMPRESSOS")){
            BPrinted bPrinted = project.getBriefing().getPrinted();
            if (bPrinted == null) throw new NullPointerException("Error retrieving the briefing: The field printed is null");
            BPrintedsDetailedView view = bPrintedsDetailedViewMapper.map(bPrinted);
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        if (briefingType.equals("LAYOUTS PARA BRINDES")){
            BGift bGift = project.getBriefing().getGift();
            if (bGift == null) throw new NullPointerException("Error retrieving the briefing: The field gift is null");
            BGiftDetailedView view = bGiftDetailedViewMapper.map(bGift);
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        if (briefingType.equals("CAMPANHAS INTERNAS")){
            BInternalCampaign bInternalCampaign = project.getBriefing().getInternalCampaign();
            if (bInternalCampaign == null) throw new NullPointerException("Error retrieving the briefing: The field internalCampaign is null");
            BInternalCampaignsDetailsView view = bInternalCampaignDetailedViewMapper.map(bInternalCampaign);
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        if (briefingType.equals("COMUNICADOS")){
            BHandout bHandout = project.getBriefing().getHandout();
            if (bHandout == null) throw new NullPointerException("Error retrieving the briefing: The field handout is null");
            BHandoutDetailedView view = bHandoutDetailedViewMapper.map(bHandout);
            return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
        }
        throw new IllegalArgumentException("Error retrieving the briefing: The project briefing type is not valid");
    }

    public void updateTitle(Long id, String title) {
        Project project = projectRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Project not found with id: " + id)
        );
        project.setTitle(title);
        projectRepository.save(project);
    }

    public void updateDate(Long id, LocalDate date) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found with id: " + id)
        );
        project.getBriefing().setExpectedTime(date);
        briefingRepository.save(project.getBriefing());
    }
}
