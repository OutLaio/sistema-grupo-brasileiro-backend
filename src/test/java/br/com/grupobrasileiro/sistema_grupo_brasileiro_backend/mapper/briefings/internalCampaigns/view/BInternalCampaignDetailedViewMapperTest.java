package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

@ExtendWith(MockitoExtension.class)
public class BInternalCampaignDetailedViewMapperTest {

    @InjectMocks
    private BInternalCampaignDetailedViewMapper mapper;

    @Mock
    private BInternalCampaignViewMapper bInternalCampaignViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap_withValidInput() {
        // Arrange
        BInternalCampaign internalCampaign = new BInternalCampaign();
        Briefing briefing = new Briefing();
        Project project = new Project();

        // Associando o briefing e o projeto
        internalCampaign.setBriefing(briefing);
        briefing.setProject(project);

        // Criando mocks para os objetos de visualização

        // Mock para OtherItemView
        OtherItemView otherItemView = new OtherItemView(1L, "Other Item Description");

        // Mock para BInternalCampaignsView
        BInternalCampaignsView campaignView = new BInternalCampaignsView(
            1L, 
            new StationeryTypeView(1L, "Papel"), 
            otherItemView, 
            "Motto"
        );

        // Mock para ProjectView
        ProjectView projectView = new ProjectView(
            1L, 
            "Project Title", 
            "Active", 
            new EmployeeSimpleView(1L, "Client", 1L), 
            new EmployeeSimpleView(2L, "Collaborator", 2L),
            null,
            LocalDate.now()
        );

        // Mock para BriefingView
        Set<CompanyView> companyViews = new HashSet<>();
        CompaniesBriefingsView companiesView = new CompaniesBriefingsView(companyViews);
        BriefingView briefingView = new BriefingView(
            1L, 
            new BriefingTypeView(1L, "Tipo A"), 
            LocalDate.now(), 
            LocalDate.now().plusDays(1), 
            LocalDate.now().plusDays(2), 
            "Description", 
            new MeasurementsView(new BigDecimal("1.0"), new BigDecimal("2.0")), 
            companiesView, 
            "Other Companies", 
            Set.of()
        );

        // Configurando os mocks para os mappers
        when(bInternalCampaignViewMapper.map(internalCampaign)).thenReturn(campaignView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Act
        BInternalCampaignsDetailsView result = mapper.map(internalCampaign);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(campaignView, result.bInternalCampaign(), "BInternalCampaignsView should match");
        assertEquals(projectView, result.project(), "ProjectView should match");
        assertEquals(briefingView, result.briefing(), "BriefingView should match");
    }



    @Test
    public void testMap_withNullBriefing() {
        // Arrange
        BInternalCampaign internalCampaign = new BInternalCampaign();
        internalCampaign.setBriefing(null);

        OtherItemView otherItemView = new OtherItemView(1L, "Other Item Description");
        BInternalCampaignsView campaignView = new BInternalCampaignsView(1L, 
                new StationeryTypeView(1L, "Papel"), otherItemView, "Motto");
        when(bInternalCampaignViewMapper.map(internalCampaign)).thenReturn(campaignView);

        // Act
        BInternalCampaignsDetailsView result = mapper.map(internalCampaign);

        // Assert
        assertNotNull(result);
        assertEquals(campaignView, result.bInternalCampaign());
        assertNull(result.project());
        assertNull(result.briefing());
    }

    @Test
    public void testMap_withNullProject() {
        // Arrange
        BInternalCampaign internalCampaign = new BInternalCampaign();
        Briefing briefing = new Briefing();
        internalCampaign.setBriefing(briefing);
        briefing.setProject(null);

        OtherItemView otherItemView = new OtherItemView(1L, "Other Item Description");
        BInternalCampaignsView campaignView = new BInternalCampaignsView(1L, 
                new StationeryTypeView(1L, "Papel"), otherItemView, "Motto");
        when(bInternalCampaignViewMapper.map(internalCampaign)).thenReturn(campaignView);
        when(briefingViewMapper.map(briefing)).thenReturn(new BriefingView(1L, 
                new BriefingTypeView(1L, "Tipo A"), LocalDate.now(), 
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 
                "Description", new MeasurementsView(new BigDecimal("1.0"), new BigDecimal("2.0")), 
                new CompaniesBriefingsView(Set.of()), "Other Companies", Set.of()));

        // Act
        BInternalCampaignsDetailsView result = mapper.map(internalCampaign);

        // Assert
        assertNotNull(result);
        assertEquals(campaignView, result.bInternalCampaign());
        assertNull(result.project());
        assertNotNull(result.briefing());
    }

    @Test
    public void testMap_withBothBriefingAndProjectNull() {
        // Arrange
        BInternalCampaign internalCampaign = new BInternalCampaign();
        internalCampaign.setBriefing(null); // Set briefing to null

        OtherItemView otherItemView = new OtherItemView(1L, "Other Item Description");
        BInternalCampaignsView campaignView = new BInternalCampaignsView(1L, 
                new StationeryTypeView(1L, "Papel"), otherItemView, "Motto");
        when(bInternalCampaignViewMapper.map(internalCampaign)).thenReturn(campaignView);

        // Act
        BInternalCampaignsDetailsView result = mapper.map(internalCampaign);

        // Assert
        assertNotNull(result);
        assertEquals(campaignView, result.bInternalCampaign());
        assertNull(result.project());
        assertNull(result.briefing());
    }
}
