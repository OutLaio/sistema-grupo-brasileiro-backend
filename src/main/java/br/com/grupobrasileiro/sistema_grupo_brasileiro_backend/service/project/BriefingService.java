package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.form.CompaniesBriefingFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.form.MeasurementFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.BriefingFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies.CompaniesBriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements.MeasurementRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private MeasurementFormMapper measurementFormMapper;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private CompaniesBriefingRepository companiesBriefingRepository;

    @Autowired
    private CompaniesBriefingFormMapper companiesBriefingFormMapper;
    

    public Briefing register(BriefingForm briefingForm, Project project) {
        BriefingType briefingType = briefingTypeRepository.findById(briefingForm.idBriefingType())
                .orElseThrow(() -> new EntityNotFoundException("BriefingType not found with ID: " + briefingForm.idBriefingType()));
        Briefing briefing = briefingFormMapper.map(briefingForm);
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);

        Measurement measurement = measurementFormMapper.map(briefingForm.measurement());
        measurement.setBriefing(briefing);

        Set<CompaniesBriefing> companies =(briefingForm.companies() != null) ? briefingForm.companies().stream().map(
                company -> {
                    CompaniesBriefing companyBriefing = companiesBriefingFormMapper.map(company);
                    companyBriefing.setBriefing(briefing);
                    return companyBriefing;
                }).collect(Collectors.toSet()) : Set.of();

        briefingRepository.saveAndFlush(briefing);
        measurementRepository.save(measurement);
        companiesBriefingRepository.saveAll(companies);
        return briefing;
    }

}
