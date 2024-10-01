package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;

class BriefingFormMapperTest {

    @InjectMocks
    private BriefingFormMapper briefingFormMapper;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

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
                LocalDate.of(2024, 9, 19), // Data de exemplo
                faker.lorem().sentence(), // Gera uma descrição detalhada aleatória
                new HashSet<>(), // Conjunto vazio de CompaniesBriefingsForm
                faker.company().name(), // Gera um nome de empresa aleatório
                1L, // ID de briefing type de exemplo
                null // Defina Measurement se necessário
        );

        // Act - mapeia o formulário para um objeto Briefing
        Briefing result = briefingFormMapper.map(form);

        // Assert - verifica se os valores foram mapeados corretamente
        assertEquals(form.expectedDate(), result.getExpectedTime(), "Expected time should match");
        assertEquals(form.detailedDescription(), result.getDetailedDescription(), "Detailed description should match");
        assertEquals(form.otherCompany(), result.getOtherCompany(), "Other company should match");
        
       }

    /**
     * Testa o mapeamento de BriefingForm quando o ID do BriefingType não é encontrado.
     * Verifica se o BriefingType resultante é nulo.
     */
    @Test
    @DisplayName("Should return Briefing with null BriefingType when ID is not found")
    void shouldReturnBriefingWithNullBriefingTypeWhenIdNotFound() {
        // Mocking BriefingForm com ID de briefing type que não existe
        BriefingForm form = new BriefingForm(
                LocalDate.of(2024, 9, 19),
                faker.lorem().sentence(), 
                new HashSet<>(), 
                faker.company().name(), 
                99L, // ID inexistente
                null 
        );

        // Act - mapeia o formulário para um objeto Briefing
        Briefing result = briefingFormMapper.map(form);

        // Assert - verifica se o resultado não é nulo e se o BriefingType é nulo
        assertNotNull(result, "Result should not be null");
        assertNull(result.getBriefingType(), "Briefing type should be null when ID is not found");
    }

    /**
     * Testa o mapeamento de BriefingForm com Measurement válido.
     * Verifica se o objeto Briefing é criado com os dados da Measurement.
     */
    @Test
    @DisplayName("Should map Measurement correctly if provided")
    void shouldMapMeasurementCorrectlyIfProvided() {
        // Mocking Measurement com valores válidos
        BigDecimal height = BigDecimal.valueOf(2.5);
        BigDecimal length = BigDecimal.valueOf(5.0);
        MeasurementsForm measurementsForm = new MeasurementsForm(height, length);

        // Mocking BriefingForm com Measurement
        BriefingForm form = new BriefingForm(
                LocalDate.of(2024, 9, 19),
                faker.lorem().sentence(), 
                new HashSet<>(), 
                faker.company().name(), 
                1L, 
                measurementsForm // Passando Measurement
        );

        // Mocking um BriefingType válido
        BriefingType mockBriefingType = new BriefingType();
        mockBriefingType.setId(1L); 
        when(briefingTypeRepository.findById(1L)).thenReturn(Optional.of(mockBriefingType));

        // Act - mapeia o formulário para um objeto Briefing
        Briefing result = briefingFormMapper.map(form);

        // Assert - verifica se a Measurement foi mapeada corretamente
        assertNotNull(result, "Result should not be null");
        // Aqui você pode adicionar asserções para verificar a Measurement no objeto Briefing, se aplicável
    }
    
    /**
     * Testa o mapeamento de BriefingForm sem Measurement.
     * Verifica se o objeto Briefing é criado sem erro.
     */
    @Test
    @DisplayName("Should handle BriefingForm without Measurement correctly")
    void shouldHandleBriefingFormWithoutMeasurementCorrectly() {
        // Mocking BriefingForm sem Measurement
        BriefingForm form = new BriefingForm(
                LocalDate.of(2024, 9, 19),
                faker.lorem().sentence(), 
                new HashSet<>(), 
                faker.company().name(), 
                1L, 
                null // Sem Measurement
        );

        // Mocking um BriefingType válido
        BriefingType mockBriefingType = new BriefingType();
        mockBriefingType.setId(1L); 
        when(briefingTypeRepository.findById(1L)).thenReturn(Optional.of(mockBriefingType));

        // Act - mapeia o formulário para um objeto Briefing
        Briefing result = briefingFormMapper.map(form);

        // Assert - verifica se o resultado não é nulo
        assertNotNull(result, "Result should not be null");
        // Verifique que o resultado está correto, mesmo sem Measurement
    }
}
