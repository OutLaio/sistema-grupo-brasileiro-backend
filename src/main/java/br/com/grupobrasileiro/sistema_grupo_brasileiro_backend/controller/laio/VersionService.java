package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.laio;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
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
            project.setStatus(ProjectStatusEnum.EM_ANDAMENTO.toString());
            projectRepository.save(project);
        } else if (version.getClientApprove() != null && !version.getClientApprove()) {
            project.setStatus(ProjectStatusEnum.APROVADO.toString());
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
            project.setStatus(ProjectStatusEnum.APROVADO.toString());
            projectRepository.save(project);
        }else {
            version.setFeedback(form.feedback());
        }

        versionRepository.save(version);
    }

}
