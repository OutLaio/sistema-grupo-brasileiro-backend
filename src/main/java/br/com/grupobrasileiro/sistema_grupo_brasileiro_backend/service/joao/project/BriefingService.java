package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.joao.project;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.BriefingFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BriefingService {

    @Autowired
    private BriefingRepository briefingRepository;

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Autowired
    private BriefingFormMapper briefingFormMapper;

    @Autowired
    private ProjectRepository projectRepository;


    public Briefing register(BriefingForm briefingForm, Project project) {
        BriefingType briefingType = briefingTypeRepository.findById(briefingForm.idBriefingType())
                .orElseThrow(() -> new EntityNotFoundException("BriefingType not found with ID: " + briefingForm.idBriefingType()));
        Briefing briefing = briefingFormMapper.map(briefingForm);
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing = briefingRepository.save(briefing);
        return briefing;
    }


}
