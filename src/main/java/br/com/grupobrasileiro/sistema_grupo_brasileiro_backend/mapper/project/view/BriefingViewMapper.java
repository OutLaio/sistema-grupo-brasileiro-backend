package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.view.CompaniesBriefingsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.view.MeasurementsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
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

    @Override
    public BriefingView map(Briefing briefing) {
        return new BriefingView(
                briefing.getId(),
                briefingTypeMapperView.map(briefing.getBriefingType()),
                briefing.getStartTime(),
                briefing.getExpectedTime(),
                briefing.getFinishTime(),
                briefing.getDetailedDescription(),
                measurementsViewMapper.map(briefing.getMeasurement()),
                companiesBriefingsViewMapper.map(briefing.getCompanies()),
                briefing.getOtherCompany()
        );
    }
}
