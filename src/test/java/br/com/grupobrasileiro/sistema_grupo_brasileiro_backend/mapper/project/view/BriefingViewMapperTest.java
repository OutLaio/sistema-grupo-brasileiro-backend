package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BriefingViewMapperTest {

    @InjectMocks
    private BriefingViewMapper briefingViewMapper;

    @Mock
    private BriefingTypeViewMapper briefingTypeViewMapper;

    private Briefing briefing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        briefing = new Briefing(); // Inicialize com dados fictícios ou mocks
        briefing.setId(1L);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(1));
        briefing.setFinishTime(LocalDateTime.now().plusDays(2));
        briefing.setDetailedDescription("Test description");
        // Configure o briefingType mock se necessário
    }

    @Test
    @DisplayName("Should map Briefing to BriefingView")
    void shouldMapBriefingToBriefingView() {
        // Arrange
        BriefingType briefingType = new BriefingType(1L, "Type description");
        briefing.setBriefingType(briefingType);

        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Type description");
        when(briefingTypeViewMapper.map(briefingType)).thenReturn(briefingTypeView);

        // Act
        BriefingView result = briefingViewMapper.map(briefing);

        // Assert
        assertNotNull(result, "Mapped BriefingView should not be null");
        assertEquals(briefing.getId(), result.id(), "BriefingView ID should match");
        assertEquals(briefingTypeView, result.briefingType(), "BriefingView type should match");
        assertEquals(briefing.getStartTime(), result.startTime(), "Start time should match");
        assertEquals(briefing.getExpectedTime(), result.expectedTime(), "Expected time should match");
        assertEquals(briefing.getFinishTime(), result.finishTime(), "Finish time should match");
        assertEquals(briefing.getDetailedDescription(), result.detailedDescription(), "Detailed description should match");
    }
}
