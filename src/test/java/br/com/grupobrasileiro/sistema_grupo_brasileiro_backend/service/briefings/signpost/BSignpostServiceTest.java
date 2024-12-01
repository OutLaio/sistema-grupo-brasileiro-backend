package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.form.BSignpostFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.SignpostRepository;

@DisplayName("BSignpostService Tests")
class BSignpostServiceTest {

    @Mock
    private SignpostRepository signpostRepository;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private BSignpostFormMapper bSignpostFormMapper;

    @InjectMocks
    private BSignpostService bSignpostService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should successfully register a BSignpost")
    void register_Successful() {
        // Arrange
        Long idMaterial = faker.number().randomNumber();
        BSignpostForm form = new BSignpostForm(
            idMaterial,
            faker.lorem().word(), // boardLocation
            faker.lorem().word()  // sector
        );

        Material material = new Material();
        BSignpost bSignpost = new BSignpost();
        
        // Criando um objeto BSignpostView e BSignpostDetailedView
        BSignpostView bSignpostView = new BSignpostView(
            faker.number().randomNumber(),
            null, form.boardLocation(),
            form.sector()
        );

        ProjectView projectView = new ProjectView(
            faker.number().randomNumber(),
            faker.lorem().word(),
            faker.lorem().word(),
            null, // Ajuste conforme necessário
            null,  // Ajuste conforme necessário
            null,
            LocalDate.now()
        );

        // Criando um objeto BriefingView com todos os campos necessários
        BriefingView briefingView = new BriefingView(
            faker.number().randomNumber(),
            new BriefingTypeView(faker.number().randomNumber(), faker.lorem().word()), // briefingType
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(2),
            faker.lorem().sentence(),
            null, // MeasurementsView
            null, // CompaniesBriefingsView
            faker.lorem().sentence(), // otherCompanies
            new HashSet<>() // versions
        );

        BSignpostDetailedView signpostRegisterView = new BSignpostDetailedView(
            bSignpostView,
            projectView,
            briefingView
        );

        Briefing briefing = new Briefing(); 

        when(materialRepository.findById(anyLong())).thenReturn(Optional.of(material));
        when(bSignpostFormMapper.map(any(BSignpostForm.class))).thenReturn(bSignpost);
        when(signpostRepository.save(any(BSignpost.class))).thenReturn(bSignpost);

        // Act
        bSignpostService.register(form, briefing); // Não espera retorno

        // Assert
        verify(materialRepository, times(1)).findById(anyLong());
        verify(bSignpostFormMapper, times(1)).map(any(BSignpostForm.class));
        verify(signpostRepository, times(1)).save(any(BSignpost.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when Material is not found")
    void register_MaterialNotFound() {
        // Arrange
        Long idMaterial = faker.number().randomNumber();
        BSignpostForm form = new BSignpostForm(
            idMaterial,
            faker.lorem().word(), // boardLocation
            faker.lorem().word()  // sector
        );

        Briefing briefing = new Briefing();

        when(materialRepository.findById(anyLong())).thenThrow(new RuntimeException("Material not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> bSignpostService.register(form, briefing),
            () -> "Expected RuntimeException to be thrown"
        );

     //   assertEquals("Material not found", exception.getMessage());
    }
}