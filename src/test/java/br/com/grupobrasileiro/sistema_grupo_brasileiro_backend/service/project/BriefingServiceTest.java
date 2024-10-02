package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
    @DisplayName("Must map and configure companies correctly")
    void shouldMapAndConfigureCompaniesCorrectly() {
        Long briefingTypeId = faker.number().randomNumber();
        CompaniesBriefingsForm companyForm = new CompaniesBriefingsForm(faker.number().randomNumber());

        BriefingForm briefingForm = new BriefingForm(
            LocalDate.now(),
            faker.lorem().paragraph(),
            Set.of(companyForm),
            faker.company().name(),
            briefingTypeId,
            mock(MeasurementsForm.class)
        );
        
    }

    @Test
    @DisplayName("Should handle correctly when briefing Form.companies() is nulll")
    void shouldHandleNullCompanies() {
        Long briefingTypeId = faker.number().randomNumber();

        BriefingForm briefingForm = new BriefingForm(
            LocalDate.now(),
            faker.lorem().paragraph(),
            null,
            faker.company().name(),
            briefingTypeId,
            mock(MeasurementsForm.class)
        );

    }
}
