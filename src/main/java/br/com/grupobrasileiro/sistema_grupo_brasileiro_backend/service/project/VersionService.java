package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ApproveForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.VersionFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.VersionViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.VersionRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VersionService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private VersionViewMapper versionViewMapper;

    @Autowired
    private VersionFormMapper versionFormMapper;

    @Autowired
    private DialogBoxService dialogBoxService;

    public VersionView supervisorApprove(Long idProject, ApproveForm form) {
        Version version = versionRepository.findById(form.idVersion()).orElseThrow(
                () -> new EntityNotFoundException("Could not find version " + form.idVersion() + " in repository")
        );

        version.setSupervisorApprove(form.approved());
        String message = "A arte da Versão #" + version.getNumVersion() + " foi aprovada pelo supervisor e está agora aguardando a sua aprovação. Por favor, revise-a na seção 'Artes Desenvolvidas' e informe sua aprovação ou solicite ajustes, se necessário.";

        if(!form.approved()){
            version.setFeedback(form.feedback());
            message = "A arte da Versão #" + version.getNumVersion() + " desenvolvida não foi aprovada pelo supervisor e será retornada para correções. O colaborador será notificado para realizar as alterações necessárias.";
        } else if (version.getClientApprove() != null && !version.getClientApprove()) {
            message = "O supervisor não aprovou a solicitação de revisão feita pelo solicitante. A arte da Versão #" + version.getNumVersion() + " desenvolvida foi, portanto, aprovada conforme original e seguirá para os próximos passos do processo.";
        }
        version = versionRepository.save(version);
        dialogBoxService.createMessage(new DialogBoxForm(0L, idProject, message));

        syncStatus(idProject);
        return versionViewMapper.map(version);
    }

    public VersionView clientApprove(Long idProject, ApproveForm form) {
        Version version = versionRepository.findById(form.idVersion()).orElseThrow(
                () -> new EntityNotFoundException("Could not find version " + form.idVersion() + " in repository")
        );
        
        version.setClientApprove(form.approved());
        String message = "A arte da Versão #" + version.getNumVersion() + " foi aprovada com sucesso! Ela seguirá para os próximos passos do processo. Agradecemos pela sua aprovação e ficamos à disposição para qualquer outra necessidade.";

        if (!form.approved()) {
            version.setSupervisorApprove(null);
            version.setFeedback(form.feedback());
            message = "A arte da Versão #" + version.getNumVersion() + " desenvolvida não foi aprovada pelo solicitante. Os ajustes solicitados são os seguintes: " + form.feedback() + ". \nO supervisor irá analisar a solicitação de revisão e, após a análise, a arte será encaminhada para os próximos passos.";
        }

        version = versionRepository.save(version);
        dialogBoxService.createMessage(new DialogBoxForm(0L, idProject, message));

        syncStatus(idProject);
        return versionViewMapper.map(version);
    }

    public VersionView create(Long idProject, NewVersionForm form) {
        Project project = projectRepository.findById(idProject).orElseThrow(
                () -> new EntityNotFoundException("Project not found with id: " + idProject)
        );
        Briefing briefing = project.getBriefing();
        long qtdVersions = versionRepository.countVersionsByBriefingId(briefing.getId());
        Version version = versionFormMapper.map(form);
        version.setBriefing(briefing);
        version.setNumVersion((int)qtdVersions + 1);

        if (project.getStatus().equals(ProjectStatusEnum.IN_PROGRESS.toString()))
            project.setStatus(ProjectStatusEnum.WAITING_APPROVAL.toString());

        projectRepository.save(project);
        version = versionRepository.save(version);

        dialogBoxService.createMessage(new DialogBoxForm(0L, idProject, "Uma nova versão da arte foi desenvolvida! A arte da Versão #" + version.getNumVersion() + " pode ser visualizada ao lado, na seção 'Artes Desenvolvidas'. Assim que aprovada pelo supervisor, a arte estará disponível para download e aprovação final."));

        return versionViewMapper.map(version);
    }


    private void syncStatus(Long idProject) {
        Project project = projectRepository.findById(idProject).orElseThrow(
                () -> new EntityNotFoundException("Could not find project " + idProject + " in repository")
        );
        Set<Version> versions = project.getBriefing().getVersions();
        Boolean hasPendingSupervisor = versions.stream().anyMatch(v -> v.getSupervisorApprove() == null);
        Boolean hasPendingClient = versions.stream().anyMatch(v -> v.getSupervisorApprove() != null && v.getSupervisorApprove() && v.getClientApprove() == null);

        if (hasPendingSupervisor || hasPendingClient)
            project.setStatus(ProjectStatusEnum.WAITING_APPROVAL.toString());
        else if (versions.stream().anyMatch(v -> v.getSupervisorApprove() && v.getClientApprove() != null))
            project.setStatus(ProjectStatusEnum.APPROVED.toString());
        else
            project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());
        projectRepository.save(project);
    }
}
