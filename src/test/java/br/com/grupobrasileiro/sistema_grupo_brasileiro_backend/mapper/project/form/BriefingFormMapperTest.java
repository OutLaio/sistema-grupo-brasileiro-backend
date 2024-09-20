package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;

class BriefingFormMapperTest {

    @InjectMocks
    private BriefingFormMapper briefingFormMapper;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    @Mock
    private ProjectRepository projectRepository; // Se necessário

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker(); // Inicializa o Faker para gerar dados aleatórios
    }

    /**
     * Testa o mapeamento de BriefingForm para Briefing.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map BriefingForm to Briefing correctly")
    void shouldMapBriefingFormToBriefing() {
        // Mocking BriefingType
        BriefingType mockBriefingType = new BriefingType();
        mockBriefingType.setId(1L); // Definindo o ID para o mock

        // Mocking a resposta do repositório
        when(briefingTypeRepository.findById(1L)).thenReturn(Optional.of(mockBriefingType));

        // Mocking BriefingForm com dados de exemplo
        BriefingForm form = new BriefingForm(
                LocalDateTime.of(2024, 9, 19, 12, 0), // Data de exemplo
                faker.lorem().sentence(), // Gera uma descrição detalhada aleatória
                new HashSet<>(), // Conjunto vazio de CompaniesBriefingsForm
                faker.company().name(), // Gera um nome de empresa aleatório
                1L, // ID de briefing type de exemplo
                null // Defina Measurement se necessário
        );

        // Act - mapeia o formulário para um objeto Briefing
        Briefing result = briefingFormMapper.map(form);

        // Assert - verifica se os valores foram mapeados corretamente
        assertEquals(form.expectedTime(), result.getExpectedTime(), "Expected time should match");
        assertEquals(form.detailedDescription(), result.getDetailedDescription(), "Detailed description should match");
        assertEquals(form.otherCompany(), result.getOtherCompany(), "Other company should match");
        
        // Verifique se o BriefingType não é nulo e se o ID corresponde
        assertNotNull(result.getBriefingType(), "Briefing type should not be null");
        assertEquals(form.idBriefingType(), result.getBriefingType().getId(), "Briefing type ID should match");
    }
}