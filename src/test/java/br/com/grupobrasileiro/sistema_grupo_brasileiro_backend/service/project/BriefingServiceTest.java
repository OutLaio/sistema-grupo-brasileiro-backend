package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
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

public class BriefingServiceTest {

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    @Mock
    private BriefingFormMapper briefingFormMapper;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MeasurementFormMapper measurementFormMapper;

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private CompaniesBriefingRepository companiesBriefingRepository;

    @Mock
    private CompaniesBriefingFormMapper companiesBriefingFormMapper;

    @InjectMocks
    private BriefingService briefingService;

    private BriefingForm briefingForm;
    private Project project;
    private BriefingType briefingType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking a Project and BriefingType
        project = mock(Project.class);
        briefingType = mock(BriefingType.class);

        // Creating a basic BriefingForm
        briefingForm = new BriefingForm(
            "Campaign Description",
            null, // Companies will be null for this test
            "Company XYZ",
            1L, // Assuming this is a valid ID for a BriefingType
            null // No Measurement for this case
        );
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when BriefingType is not found")
    void shouldThrowEntityNotFoundExceptionWhenBriefingTypeIsNotFound() {
        // Arrange: Mock the repository to return empty when finding the BriefingType by ID
        when(briefingTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert: Assert that the exception is thrown
        assertThrows(EntityNotFoundException.class, () -> {
            briefingService.register(briefingForm, project);
        });

        // Verify no save was attempted
        verify(briefingRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should handle null companies correctly")
    void shouldHandleNullCompaniesCorrectly() {
        // Arrange: Mock the repository methods
        when(briefingTypeRepository.findById(anyLong())).thenReturn(Optional.of(briefingType));
        when(briefingFormMapper.map(any(BriefingForm.class))).thenReturn(new Briefing());
        
        // Act: Calling register with the briefing form with null companies
        Briefing result = briefingService.register(briefingForm, project);

        // Assert: Verify that no companies were saved
        assertNotNull(result);
        verify(companiesBriefingRepository, never()).saveAll(anySet());
    }

    @Test
    @DisplayName("Should save the Briefing, Measurement, and Companies correctly")
    void shouldSaveEntitiesCorrectly() {
        // Arrange: Mocking repository returns
        when(briefingTypeRepository.findById(anyLong())).thenReturn(Optional.of(briefingType));

        // Mocking mappers
        Briefing mockedBriefing = mock(Briefing.class);
        when(briefingFormMapper.map(any(BriefingForm.class))).thenReturn(mockedBriefing);

        // Mocking the creation of a Measurement (optional in this case)
        Measurement mockedMeasurement = mock(Measurement.class);
        when(measurementFormMapper.map(any())).thenReturn(mockedMeasurement);

        // Mocking CompaniesBriefing processing
        CompaniesBriefing mockedCompany = mock(CompaniesBriefing.class);
        when(companiesBriefingFormMapper.map(any())).thenReturn(mockedCompany);

        // Create the form with companies and a measurement
        briefingForm = new BriefingForm(
            "Campaign Description",
            Set.of(new CompaniesBriefingsForm(123L)), // Companies
            "Company XYZ",
            1L, // Assuming this is a valid ID for a BriefingType
            null // No Measurement for this case
        );

        // Act: Calling the service
        Briefing result = briefingService.register(briefingForm, project);

        // Assert: Verifications
        assertNotNull(result);
        verify(briefingRepository).saveAndFlush(mockedBriefing);
        verify(measurementRepository).save(mockedMeasurement);
        verify(companiesBriefingRepository).saveAll(anySet());
    }
}
