package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public VersionView supervisorApprove(Long idProject, ApproveForm form) {
        Project project = projectRepository.findById(idProject).orElseThrow(
                () -> new EntityNotFoundException("Could not find projectForm " + idProject + " in repository")
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
        version = versionRepository.save(version);
        return versionViewMapper.map(version);
    }

    public VersionView clientApprove(Long idProject, ApproveForm form) {
        Project project = projectRepository.findById(idProject).orElseThrow(
                () -> new EntityNotFoundException("Could not find projectForm " + idProject + " in repository")
        );

        Version version = versionRepository.findById(form.idVersion()).orElseThrow(
                () -> new EntityNotFoundException("Could not find version " + form.idVersion() + " in repository")
        );
        
        version.setClientApprove(form.approved());

        if (form.approved()) {
            project.setStatus(ProjectStatusEnum.APPROVED.toString());
            projectRepository.save(project);
        }else {
            version.setSupervisorApprove(null);
            version.setFeedback(form.feedback());
        }

        version = versionRepository.save(version);
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
        return versionViewMapper.map(version);
    }
}
