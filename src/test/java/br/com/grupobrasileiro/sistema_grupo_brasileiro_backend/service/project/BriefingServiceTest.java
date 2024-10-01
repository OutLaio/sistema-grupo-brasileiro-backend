package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
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

@DisplayName("BriefingService Tests")
public class BriefingServiceTest {

	@Mock
	private BriefingTypeRepository briefingTypeRepository;

	@Mock
	private BriefingFormMapper briefingFormMapper;

	@Mock
	private MeasurementFormMapper measurementFormMapper;

	@Mock
	private MeasurementRepository measurementRepository;

	@Mock
	private CompaniesBriefingRepository companiesBriefingRepository;

	@Mock
	private BriefingRepository briefingRepository;

	@Mock
	private CompaniesBriefingFormMapper companiesBriefingFormMapper;

	@InjectMocks
	private BriefingService briefingService;

	private final Faker faker = new Faker();

    public BriefingServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve mapear e configurar empresas corretamente")
    void shouldMapAndConfigureCompaniesCorrectly() {
        Long briefingTypeId = faker.number().randomNumber();
        CompaniesBriefingsForm companyForm = new CompaniesBriefingsForm(faker.number().randomNumber());

        BriefingForm briefingForm = new BriefingForm(
            LocalDateTime.now(),
            faker.lorem().paragraph(),
            Set.of(companyForm), // Lista de empresas não é nula
            faker.company().name(),
            briefingTypeId,
            mock(MeasurementsForm.class)
        );

        BriefingType briefingType = new BriefingType();
        Briefing briefing = new Briefing();
        Measurement measurement = new Measurement();
        CompaniesBriefing companyBriefing = new CompaniesBriefing();
        
        when(briefingTypeRepository.findById(briefingTypeId)).thenReturn(Optional.of(briefingType));
        when(briefingFormMapper.map(briefingForm)).thenReturn(briefing);
        when(measurementFormMapper.map(briefingForm.measurement())).thenReturn(measurement);
        when(companiesBriefingFormMapper.map(companyForm)).thenReturn(companyBriefing);
        
        // Execute o método
        Briefing result = briefingService.register(briefingForm, mock(Project.class));

        // Verifique se o mapeamento foi feito corretamente
        assertNotNull(result.getCompanies());
        assertEquals(1, result.getCompanies().size());
        CompaniesBriefing resultCompanyBriefing = result.getCompanies().iterator().next();
        assertEquals(companyBriefing, resultCompanyBriefing);
        assertEquals(briefing, resultCompanyBriefing.getBriefing());
        
        // Verifique se os métodos de mapeamento foram chamados
        verify(companiesBriefingFormMapper, times(1)).map(companyForm);
    }

    @Test
    @DisplayName("Deve lidar corretamente quando briefingForm.companies() for null")
    void shouldHandleNullCompanies() {
        Long briefingTypeId = faker.number().randomNumber();

        BriefingForm briefingForm = new BriefingForm(
            LocalDateTime.now(),
            faker.lorem().paragraph(),
            null, // Lista de empresas é null
            faker.company().name(),
            briefingTypeId,
            mock(MeasurementsForm.class)
        );

        BriefingType briefingType = new BriefingType();
        Briefing briefing = new Briefing();
        Measurement measurement = new Measurement();

        when(briefingTypeRepository.findById(briefingTypeId)).thenReturn(Optional.of(briefingType));
        when(briefingFormMapper.map(briefingForm)).thenReturn(briefing);
        when(measurementFormMapper.map(briefingForm.measurement())).thenReturn(measurement);

        // Execute o método
        Briefing result = briefingService.register(briefingForm, mock(Project.class));

        // Verifique se a lista de empresas está vazia
        assertNotNull(result.getCompanies());
        assertTrue(result.getCompanies().isEmpty());
        
        // Verifique se os métodos de mapeamento não foram chamados
        verify(companiesBriefingFormMapper, times(0)).map(any(CompaniesBriefingsForm.class));
    }

    
}
