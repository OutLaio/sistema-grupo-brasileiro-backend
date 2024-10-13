package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.view.CompaniesBriefingsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.view.MeasurementsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies.CompaniesBriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements.MeasurementRepository;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BriefingViewMapper implements Mapper<Briefing, BriefingView> {

    @Autowired
    private BriefingTypeViewMapper briefingTypeMapperView;

    @Autowired
    private MeasurementsViewMapper measurementsViewMapper;

    @Autowired
    private CompaniesBriefingsViewMapper companiesBriefingsViewMapper;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private CompaniesBriefingRepository companiesBriefingRepository;

    @Override
    public BriefingView map(Briefing briefing) {
        Measurement measurement = measurementRepository.findByBriefingId(briefing.getId());
        Set<CompaniesBriefing> companiesBriefing= companiesBriefingRepository.findByBriefingId(briefing.getId());
        return new BriefingView(
                briefing.getId(),
                briefingTypeMapperView.map(briefing.getBriefingType()),
                briefing.getStartTime(),
                briefing.getExpectedTime(),
                briefing.getFinishTime(),
                briefing.getDetailedDescription(),
                measurementsViewMapper.map(measurement),
                companiesBriefingsViewMapper.map(companiesBriefing),
                briefing.getOtherCompany()
        );
    }
}
