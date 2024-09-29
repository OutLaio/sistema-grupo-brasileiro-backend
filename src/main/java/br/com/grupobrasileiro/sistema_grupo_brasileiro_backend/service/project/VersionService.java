package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ApproveForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VersionRepository versionRepository;

    public void supervisorApprove(ApproveForm form) {
        Project project = projectRepository.findById(form.idProject()).orElseThrow(
                () -> new EntityNotFoundException("Could not find project " + form.idProject() + " in repository")
        );

        Version version = versionRepository.findById(form.idVersion()).orElseThrow(
                () -> new EntityNotFoundException("Could not find version " + form.idVersion() + " in repository")
        );

        version.setSupervisorApprove(form.approved());

        if(!form.approved()){
            version.setFeedback(form.feedback());
            project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());
            projectRepository.save(project);
        } else if (version.getClientApprove() != null && !version.getClientApprove()) {
            project.setStatus(ProjectStatusEnum.APPROVED.toString());
            projectRepository.save(project);
        }

        versionRepository.save(version);
    }

    public void clientApprove(ApproveForm form) {
        Project project = projectRepository.findById(form.idProject()).orElseThrow(
                () -> new EntityNotFoundException("Could not find project " + form.idProject() + " in repository")
        );

        Version version = versionRepository.findById(form.idVersion()).orElseThrow(
                () -> new EntityNotFoundException("Could not find version " + form.idVersion() + " in repository")
        );
        
        version.setClientApprove(form.approved());

        if (form.approved()) {
            project.setStatus(ProjectStatusEnum.APPROVED.toString());
            projectRepository.save(project);
        }else {
            version.setFeedback(form.feedback());
        }

        versionRepository.save(version);
    }

    public void create(Long idProject, NewVersionForm form) {
        Project project = projectRepository.findById(idProject).orElseThrow(
                () -> new EntityNotFoundException("Project not found with id: " + idProject)
        );
        Briefing briefing = project.getBriefing();
        long qtdVersions = versionRepository.countVersionsByBriefingId(briefing.getId());
        Version version = new Version(
            null,
            briefing,
            (int) qtdVersions+1,
            form.productLink(),
            null,
            null,
            null
        );

        if (project.getStatus().equals(ProjectStatusEnum.IN_PROGRESS.toString()))
            project.setStatus(ProjectStatusEnum.WAITING_APPROVAL.toString());

        projectRepository.save(project);
        versionRepository.save(version);
    }
}
