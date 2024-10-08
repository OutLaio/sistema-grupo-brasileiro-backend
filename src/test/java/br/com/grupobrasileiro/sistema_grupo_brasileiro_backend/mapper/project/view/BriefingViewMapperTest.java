package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.view.CompaniesBriefingsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.view.MeasurementsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import java.util.Set;
import java.util.HashSet;

class BriefingViewMapperTest {

    private BriefingViewMapper briefingViewMapper;
    private Briefing briefing;

    @BeforeEach
    void setUp() throws Exception {
        briefingViewMapper = new BriefingViewMapper(); 

        Project project = new Project();
        project.setId(1L);

        BriefingType briefingType = new BriefingType();
        briefingType.setId(1L);
        briefingType.setDescription("Tipo de Briefing");

        briefing = new Briefing();
        briefing.setId(1L);
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(1));
        briefing.setFinishTime(LocalDate.now().plusDays(2));
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefing.setOtherCompany("Outra empresa");

        Measurement measurement = new Measurement();
        measurement.setHeight(new BigDecimal("180.5"));
        measurement.setLength(new BigDecimal("90.0"));
        briefing.setMeasurement(measurement);

        Set<CompaniesBriefing> companies = new HashSet<>();
        // Adicione alguns CompaniesBriefing ao set, se necessário
        briefing.setCompanies(companies);

        // Configure os mappers
        BriefingTypeViewMapper briefingTypeViewMapper = new BriefingTypeViewMapper() {
            @Override
            public BriefingTypeView map(BriefingType briefingType) {
                return new BriefingTypeView(briefingType.getId(), briefingType.getDescription());
            }
        };

        MeasurementsViewMapper measurementsViewMapper = new MeasurementsViewMapper() {
            @Override
            public MeasurementsView map(Measurement measurement) {
                return new MeasurementsView(measurement.getHeight(), measurement.getLength());
            }
        };

        CompanyViewMapper companyViewMapper = new CompanyViewMapper() {
            @Override
            public CompanyView map(Company company) {
                return new CompanyView(company.getId(), company.getName());
            }
        };

        CompaniesBriefingsViewMapper companiesBriefingsViewMapper = new CompaniesBriefingsViewMapper();
        
        // Configure o companyViewMapper usando reflexão
        Field companyViewMapperField = CompaniesBriefingsViewMapper.class.getDeclaredField("companyViewMapper");
        companyViewMapperField.setAccessible(true);
        companyViewMapperField.set(companiesBriefingsViewMapper, companyViewMapper);

        // Configure os mappers usando reflexão
        Field briefingTypeMapperField = BriefingViewMapper.class.getDeclaredField("briefingTypeMapperView");
        briefingTypeMapperField.setAccessible(true);
        briefingTypeMapperField.set(briefingViewMapper, briefingTypeViewMapper);

        Field measurementsMapperField = BriefingViewMapper.class.getDeclaredField("measurementsViewMapper");
        measurementsMapperField.setAccessible(true);
        measurementsMapperField.set(briefingViewMapper, measurementsViewMapper);

        Field companiesBriefingsMapperField = BriefingViewMapper.class.getDeclaredField("companiesBriefingsViewMapper");
        companiesBriefingsMapperField.setAccessible(true);
        companiesBriefingsMapperField.set(briefingViewMapper, companiesBriefingsViewMapper);
    }
    @Test
    @DisplayName("Must map Briefing to BriefingView")
    void mapBriefingToBriefingView() {
        BriefingView result = briefingViewMapper.map(briefing);

        assertNotNull(result, "O BriefingView mapeado não deve ser nulo");
        assertEquals(briefing.getId(), result.id(), "O ID do BriefingView deve corresponder");
        assertNotNull(result.briefingType(), "O BriefingType não deve ser nulo");
        assertEquals(briefing.getBriefingType().getId(), result.briefingType().id(), "O ID do tipo de briefing deve corresponder");
        assertEquals(briefing.getBriefingType().getDescription(), result.briefingType().description(), "A descrição do tipo de briefing deve corresponder");
        assertEquals(briefing.getStartTime(), result.startTime(), "A data de início deve corresponder");
        assertEquals(briefing.getExpectedTime(), result.expectedTime(), "A data esperada deve corresponder");
        assertEquals(briefing.getFinishTime(), result.finishTime(), "A data de término deve corresponder");
        assertEquals(briefing.getDetailedDescription(), result.detailedDescription(), "A descrição detalhada deve corresponder");
        assertEquals(briefing.getOtherCompany(), result.otherCompanies(), "Outras empresas devem corresponder");
        
        assertNotNull(result.measurements(), "O Measurements não deve ser nulo");
        assertEquals(briefing.getMeasurement().getHeight(), result.measurements().height(), "A altura do Measurements deve corresponder");
        assertEquals(briefing.getMeasurement().getLength(), result.measurements().length(), "O comprimento do Measurements deve corresponder");

        assertNotNull(result.companies(), "O CompaniesBriefingsView não deve ser nulo");
        assertNotNull(result.companies().companies(), "O conjunto de CompanyView não deve ser nulo");
        assertEquals(briefing.getCompanies().size(), result.companies().companies().size(), "O número de empresas deve corresponder");
        
        // Verifique se cada empresa foi mapeada corretamente
        for (CompaniesBriefing cb : briefing.getCompanies()) {
            CompanyView companyView = result.companies().companies().stream()
                .filter(cv -> cv.id().equals(cb.getCompany().getId()))
                .findFirst()
                .orElse(null);
            assertNotNull(companyView, "A empresa deve estar presente no resultado mapeado");
            assertEquals(cb.getCompany().getName(), companyView.name(), "O nome da empresa deve corresponder");
        }
    }
           

    @Test
    @DisplayName("Must map Briefing with valid data to BriefingView")
    void mapBriefingWithValidData() {
        BriefingView result = briefingViewMapper.map(briefing);

        assertNotNull(result, "O BriefingView mapeado não deve ser nulo");
        assertEquals(briefing.getId(), result.id(), "O ID do BriefingView deve corresponder");
        assertEquals(briefing.getStartTime(), result.startTime(), "A data de início deve corresponder");
        assertEquals(briefing.getExpectedTime(), result.expectedTime(), "A data esperada deve corresponder");
        assertEquals(briefing.getFinishTime(), result.finishTime(), "A data de término deve corresponder");
        assertEquals(briefing.getDetailedDescription(), result.detailedDescription(), "A descrição detalhada deve corresponder");
        assertNotNull(result.briefingType(), "O BriefingType não deve ser nulo");
        assertNotNull(result.measurements(), "O Measurements não deve ser nulo");
        assertEquals(briefing.getMeasurement().getHeight(), result.measurements().height(), "A altura do Measurements deve corresponder");
        assertEquals(briefing.getMeasurement().getLength(), result.measurements().length(), "O comprimento do Measurements deve corresponder");
    }
    
    @Test
    @DisplayName("Must map Briefing with empty description to BriefingView")
    void mapBriefingWithEmptyDescription() {
        briefing.setDetailedDescription("");

        BriefingView result = briefingViewMapper.map(briefing);

        assertNotNull(result, "O BriefingView mapeado não deve ser nulo");
        assertEquals("", result.detailedDescription(), "A descrição detalhada deve estar vazia");
    }
}