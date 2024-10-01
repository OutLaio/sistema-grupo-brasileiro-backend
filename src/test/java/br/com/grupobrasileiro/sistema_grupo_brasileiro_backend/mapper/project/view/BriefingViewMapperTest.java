package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BriefingViewMapperTest {

    private BriefingViewMapper briefingViewMapper;
    private Briefing briefing;

    @BeforeEach
    void setUp() throws Exception {
        briefingViewMapper = new BriefingViewMapper(); 

        // Inicializa o briefing com valores fictícios
        briefing = new Briefing();
        briefing.setId(1L);  // ID fictício
        briefing.setStartTime(LocalDate.now());  // Tempo de início atual
        briefing.setExpectedTime(LocalDate.now().plusDays(1));  // Tempo esperado para o dia seguinte
        briefing.setFinishTime(LocalDate.now().plusDays(2));  // Tempo de finalização para dois dias depois
        briefing.setDetailedDescription("Test description");  // Descrição de teste

        // Criando e associando um tipo de briefing
        BriefingType briefingType = new BriefingType(1L, "Type description");
        briefing.setBriefingType(briefingType);  // Associando o tipo de briefing

        // Simula o comportamento do BriefingTypeViewMapper
        BriefingTypeViewMapper briefingTypeViewMapper = new BriefingTypeViewMapper() {
            @Override
            public BriefingTypeView map(BriefingType briefingType) {
                return new BriefingTypeView(briefingType.getId(), briefingType.getDescription());
            }
        };

        // Usa reflexão para acessar o campo briefingTypeMapperView e setá-lo
        Field briefingTypeMapperField = BriefingViewMapper.class.getDeclaredField("briefingTypeMapperView");
        briefingTypeMapperField.setAccessible(true); // Permite o acesso ao campo privado
        briefingTypeMapperField.set(briefingViewMapper, briefingTypeViewMapper); // Define o valor do campo
    }
    /**
     * Testa o método map do BriefingViewMapper.
     * Verifica se ele mapeia um briefing corretamente para um BriefingView.
     */
    @Test
    @DisplayName("Should map Briefing to BriefingView")
    void mapBriefingToBriefingView() {
        // Act
        BriefingView result = briefingViewMapper.map(briefing);

        // Assert
        assertNotNull(result, "Mapped BriefingView should not be null");
        assertEquals(briefing.getId(), result.id(), "BriefingView ID should match");
        assertEquals(briefing.getBriefingType().getId(), result.briefingType().id(), "BriefingView type ID should match");
        assertEquals(briefing.getStartTime(), result.startTime(), "Start time should match");
        assertEquals(briefing.getExpectedTime(), result.expectedTime(), "Expected time should match");
        assertEquals(briefing.getFinishTime(), result.finishTime(), "Finish time should match");
        assertEquals(briefing.getDetailedDescription(), result.detailedDescription(), "Detailed description should match");
    }

   

    /**
     * Testa o método map do BriefingViewMapper.
     * Verifica se ele lida corretamente com um briefing sem data de término.
     */
    @Test
    @DisplayName("Should map Briefing with null finish time to BriefingView")
    void mapBriefingWithNullFinishTime() {
        // Arrange
        briefing.setFinishTime(null); // Remove a data de finalização

        // Act
        BriefingView result = briefingViewMapper.map(briefing);

        // Assert
        assertNotNull(result, "Mapped BriefingView should not be null");
        assertNull(result.finishTime(), "Finish time should be null in BriefingView");
    }

    /**
     * Testa o método map do BriefingViewMapper.
     * Verifica se ele mapeia corretamente um briefing com dados de teste.
     */
    @Test
    @DisplayName("Should map Briefing with valid data to BriefingView")
    void mapBriefingWithValidData() {
        // Act
        BriefingView result = briefingViewMapper.map(briefing);

        // Assert
        assertNotNull(result, "Mapped BriefingView should not be null");
        assertEquals(briefing.getId(), result.id(), "BriefingView ID should match");
        assertEquals(briefing.getStartTime(), result.startTime(), "Start time should match");
        assertEquals(briefing.getExpectedTime(), result.expectedTime(), "Expected time should match");
        assertEquals(briefing.getDetailedDescription(), result.detailedDescription(), "Detailed description should match");
    }

    /**
     * Testa o método map do BriefingViewMapper.
     * Verifica se ele lida corretamente com um briefing com descrição vazia.
     */
    @Test
    @DisplayName("Should map Briefing with empty description to BriefingView")
    void mapBriefingWithEmptyDescription() {
        // Arrange
        briefing.setDetailedDescription(""); // Define a descrição como vazia

        // Act
        BriefingView result = briefingViewMapper.map(briefing);

        // Assert
        assertNotNull(result, "Mapped BriefingView should not be null");
        assertEquals("", result.detailedDescription(), "Detailed description should be empty");
    }
}
