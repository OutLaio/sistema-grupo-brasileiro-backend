package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;

public class BInternalCampaignsDetailsViewTest {

	@Test
	public void testCreateBInternalCampaignsDetailsView() {
	    // Arrange
	    // Criando objetos mockados necessários para o teste
	    StationeryTypeView stationeryType = new StationeryTypeView(1L, "Tipo de Papel");
	    OtherItemView otherItem = new OtherItemView(1L, "Item Adicional");
	    String campaignMotto = "Nosso Lema da Campanha";

	    BInternalCampaignsView bInternalCampaign = new BInternalCampaignsView(1L, stationeryType, otherItem, campaignMotto);

	    // Criando os objetos do projeto
	    Long projectId = 1L;
	    String projectTitle = "Título do Projeto";
	    String projectStatus = "Em Progresso";

	    // Instanciando EmployeeSimpleView com dados fictícios
	    EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente Exemplo", 1L);
	    EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador Exemplo", 2L);

	    // Criando o objeto ProjectView
	    ProjectView project = new ProjectView(projectId, projectTitle, projectStatus, client, collaborator, "Tipo de Briefing");

	    // Criando os dados de Briefing
	    Long briefingId = 1L;
	    LocalDate startTime = LocalDate.now();
	    LocalDate expectedTime = LocalDate.now().plusDays(5);
	    LocalDate finishTime = LocalDate.now().plusDays(10);
	    String detailedDescription = "Descrição detalhada do briefing.";
	    Set<VersionView> versions = new HashSet<>();

	    // Criando o objeto BriefingView
	    BriefingView briefing = new BriefingView(briefingId, null, startTime, expectedTime, finishTime,
	            detailedDescription, null, null, null, versions);

	    // Act
	    BInternalCampaignsDetailsView detailsView = new BInternalCampaignsDetailsView(bInternalCampaign, project, briefing);

	    // Assert
	    assertEquals(bInternalCampaign, detailsView.bInternalCampaign());
	    assertEquals(project, detailsView.project());
	    assertEquals(briefing, detailsView.briefing());
	}

	@Test
	public void testEqualsAndHashCode() {
	    // Arrange
	    StationeryTypeView stationeryType = new StationeryTypeView(1L, "Tipo de Papel");
	    OtherItemView otherItem = new OtherItemView(1L, "Item Adicional");
	    String campaignMotto = "Nosso Lema da Campanha";

	    // Criando duas instâncias de BInternalCampaignsView com dados ligeiramente diferentes
	    BInternalCampaignsView bInternalCampaign1 = new BInternalCampaignsView(1L, stationeryType, otherItem, campaignMotto);
	    BInternalCampaignsView bInternalCampaign2 = new BInternalCampaignsView(2L, stationeryType, otherItem, campaignMotto);

	    // Dados do Projeto
	    Long projectId = 1L;
	    String projectTitle = "Título do Projeto";
	    String projectStatus = "Em Progresso";

	    // Criando EmployeeSimpleView com dados fictícios
	    EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente Exemplo", 1L);
	    EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador Exemplo", 2L);

	    // Criando o objeto ProjectView
	    ProjectView project1 = new ProjectView(projectId, projectTitle, projectStatus, client, collaborator, "Tipo de Briefing");

	    // Dados do Briefing
	    Long briefingId = 1L;
	    LocalDate startTime = LocalDate.now();
	    LocalDate expectedTime = LocalDate.now().plusDays(5);
	    LocalDate finishTime = LocalDate.now().plusDays(10);
	    String detailedDescription = "Descrição detalhada do briefing.";
	    Set<VersionView> versions = new HashSet<>();

	    // Criando o objeto BriefingView
	    BriefingView briefing1 = new BriefingView(briefingId, null, startTime, expectedTime, finishTime,
	            detailedDescription, null, null, null, versions);

	    // Criando as instâncias de BInternalCampaignsDetailsView
	    BInternalCampaignsDetailsView detailsView1 = new BInternalCampaignsDetailsView(bInternalCampaign1, project1, briefing1);
	    BInternalCampaignsDetailsView detailsView2 = new BInternalCampaignsDetailsView(bInternalCampaign1, project1, briefing1);
	    BInternalCampaignsDetailsView detailsView3 = new BInternalCampaignsDetailsView(bInternalCampaign2, project1, briefing1);

	    // Act & Assert
	    // Verificando igualdade
	    assertEquals(detailsView1, detailsView2); // devem ser iguais
	    assertNotEquals(detailsView1, detailsView3); // não devem ser iguais

	    // Verificando hashCode
	    assertEquals(detailsView1.hashCode(), detailsView2.hashCode()); // mesmos hash codes para objetos iguais
	    assertNotEquals(detailsView1.hashCode(), detailsView3.hashCode()); // hash codes diferentes para objetos diferentes
	}
}