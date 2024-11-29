package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.BPrintedsDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.BStickerDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    @Autowired
    private DialogBoxService dialogBoxService;


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

        String isFirst = " ";
        if(!employee.getUser().getProfile().getId().equals(2L))
            throw new InvalidProfileException("The employee is not a Collaborator");

        if(project.getCollaborator() != null){
            isFirst = " novo ";
            if(project.getCollaborator().equals(employee))
                throw new CollaboratorAlreadyAssignedException("Collaborator is already assigned to the project");
        }

        project.setCollaborator(employee);

        if(project.getStatus().equals(ProjectStatusEnum.TO_DO.toString()))
            project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());

        project.setCollaborator(employee);
        projectRepository.save(project);

        String message = "Um" + isFirst +"colaborador foi designado para o seu projeto! " + employee.getFullName() + " estará acompanhando a solicitação e qualquer atualização será compartilhada aqui no chat. Fique à vontade para conversar sobre o andamento ou tirar dúvidas diretamente por aqui.";
        dialogBoxService.createMessage(new DialogBoxForm(0L, id, message));
    }

    public void setHasConfection(Long id, boolean hasConfection) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found for id: " + id)
        );
        Briefing briefing = project.getBriefing();
        String message = "O projeto foi encaminhado para confecção! Em breve, você receberá mais informações sobre o andamento. Fique à vontade para entrar em contato caso tenha alguma dúvida.";
        if(hasConfection)
            project.setStatus(ProjectStatusEnum.IN_PRODUCTION.toString());
        else{
            project.setStatus(ProjectStatusEnum.COMPLETED.toString());
            briefing.setFinishTime(LocalDate.now());
            briefingRepository.save(briefing);
            message = "A sua solicitação foi finalizada com sucesso! O processo foi concluído e o chat será encerrado. Se precisar de mais alguma coisa no futuro, não hesite em nos chamar. Obrigado!";
        }
        dialogBoxService.createMessage(new DialogBoxForm(0L, id, message));
        projectRepository.save(project);
    }

    public void setFinished(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found for id: " + id)
        );
        Briefing briefing = project.getBriefing();

        briefing.setFinishTime(LocalDate.now());
        project.setStatus(ProjectStatusEnum.COMPLETED.toString());

        dialogBoxService.createMessage(new DialogBoxForm(0L, id, "A sua solicitação foi finalizada com sucesso! O processo foi concluído e o chat será encerrado. Se precisar de mais alguma coisa no futuro, não hesite em nos chamar. Obrigado!"));

        briefingRepository.save(briefing);
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
        switch (briefingType) {
            case "PLACA DE ITINERÁRIOS" -> {
                BAgencyBoard bAgencyBoard = project.getBriefing().getAgencyBoard();
                if (bAgencyBoard == null)
                    throw new NullPointerException("Error retrieving the briefing: The field agencyBoard is null" );
                BAgencyBoardDetailedView view = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
            case "PLACA DE SINALIZAÇÃO INTERNA" -> {
                BSignpost bSignpost = project.getBriefing().getSignpost();
                if (bSignpost == null)
                    throw new NullPointerException("Error retrieving the briefing: The field signpost is null" );
                BSignpostDetailedView view = bSignpostDetailedViewMapper.map(bSignpost);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
            case "ADESIVOS" -> {
                BSticker bSticker = project.getBriefing().getSticker();
                if (bSticker == null)
                    throw new NullPointerException("Error retrieving the briefing: The field sticker is null" );
                BStickerDetailedView view = bStickerDetailedViewMapper.map(bSticker);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
            case "IMPRESSOS" -> {
                BPrinted bPrinted = project.getBriefing().getPrinted();
                if (bPrinted == null)
                    throw new NullPointerException("Error retrieving the briefing: The field printed is null" );
                BPrintedsDetailedView view = bPrintedsDetailedViewMapper.map(bPrinted);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
            case "LAYOUTS PARA BRINDES" -> {
                BGift bGift = project.getBriefing().getGift();
                if (bGift == null)
                    throw new NullPointerException("Error retrieving the briefing: The field gift is null" );
                BGiftDetailedView view = bGiftDetailedViewMapper.map(bGift);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
            case "CAMPANHAS INTERNAS" -> {
                BInternalCampaign bInternalCampaign = project.getBriefing().getInternalCampaign();
                if (bInternalCampaign == null)
                    throw new NullPointerException("Error retrieving the briefing: The field internalCampaign is null" );
                BInternalCampaignsDetailsView view = bInternalCampaignDetailedViewMapper.map(bInternalCampaign);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
            case "COMUNICADOS" -> {
                BHandout bHandout = project.getBriefing().getHandout();
                if (bHandout == null)
                    throw new NullPointerException("Error retrieving the briefing: The field handout is null" );
                BHandoutDetailedView view = bHandoutDetailedViewMapper.map(bHandout);
                return ResponseEntity.ok().body(new Response<>("Briefing encontrado com sucesso", view));
            }
        }
        throw new IllegalArgumentException("Error retrieving the briefing: The project briefing type is not valid");
    }

    public void updateTitle(Long id, String title) {
        Project project = projectRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Project not found with id: " + id)
        );
        String oldTitle = project.getTitle();
        project.setTitle(title);
        projectRepository.save(project);
        dialogBoxService.createMessage(new DialogBoxForm(0L, id, "Informamos que o título do projeto foi alterado de '" + oldTitle + "' para '" + title + "' para uma melhor identificação da solicitação."));
    }

    public void updateDate(Long id, LocalDate date) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found with id: " + id)
        );
        LocalDate oldDate = project.getBriefing().getExpectedTime();
        project.getBriefing().setExpectedTime(date);
        briefingRepository.save(project.getBriefing());
        String message = "Informamos que a data de entrega prevista foi definida para " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";
        if (oldDate != null)
            message = "Informamos que a data de entrega prevista foi alterada de " + oldDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " para " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";
        dialogBoxService.createMessage(new DialogBoxForm(0L, id, message));
    }

    public void updateStatus(Long id, ProjectStatusEnum status) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Project not found with id: " + id)
        );

        ProjectStatusEnum oldStatus = ProjectStatusEnum.valueOf(project.getStatus());

        project.setStatus(status.toString());
        projectRepository.save(project);

        String message = "Informamos que o status da solicitação foi alterado de '" + oldStatus.getDescription() + "' para '" + status.getDescription() + "'. Se precisar de mais informações ou tiver alguma dúvida, nos avise.";
        if (status.equals(ProjectStatusEnum.COMPLETED)) {
            message = "A sua solicitação foi finalizada! O processo foi concluído e o chat será encerrado. Se precisar de uma nova solicitação no futuro, estaremos à disposição. Obrigado!";
            project.getBriefing().setFinishTime(LocalDate.now());
            briefingRepository.save(project.getBriefing());
        }
        dialogBoxService.createMessage(new DialogBoxForm(0L, id, message));
    }
}
