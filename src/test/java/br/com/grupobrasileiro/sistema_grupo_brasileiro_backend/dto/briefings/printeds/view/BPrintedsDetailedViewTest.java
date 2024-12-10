package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;

import java.time.LocalDate;

public class BPrintedsDetailedViewTest {

    @Test
    public void testCreateBPrintedsDetailedView() {
        // Arrange
        // Criando os objetos necessários para o teste
        PrintedTypeView printedType = new PrintedTypeView(1L, "Tipo de Impressão A");
        PrintingTypeView printingType = new PrintingTypeView(1L, "Tipo de Impressão B");
        PrintedView printed = new PrintedView(1L, printedType, printingType, "Papel A", 2, 10);
        
        // Criando EmployeeSimpleView para os campos client e collaborator do ProjectView
        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente Exemplo", 1L);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador Exemplo", 2L);

        // Criando o ProjectView com client, collaborator e briefingType
        ProjectView project = new ProjectView(1L, "Projeto A", "Em andamento", client, collaborator, "Tipo de Briefing", LocalDate.now());

        // Criando o BriefingView
        BriefingView briefing = new BriefingView(1L, null, null, null, null, null, null, null, null, null);

        // Act
        BPrintedsDetailedView detailedView = new BPrintedsDetailedView(printed, project, briefing);

        // Assert
        assertEquals(printed, detailedView.printed());
        assertEquals(project, detailedView.project());
        assertEquals(briefing, detailedView.briefing());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        PrintedTypeView printedType1 = new PrintedTypeView(1L, "Tipo de Impressão A");
        PrintingTypeView printingType1 = new PrintingTypeView(1L, "Tipo de Impressão B");
        PrintedView printed1 = new PrintedView(1L, printedType1, printingType1, "Papel A", 2, 10);
        
        PrintedTypeView printedType2 = new PrintedTypeView(2L, "Tipo de Impressão C");
        PrintingTypeView printingType2 = new PrintingTypeView(2L, "Tipo de Impressão D");
        PrintedView printed2 = new PrintedView(2L, printedType2, printingType2, "Papel B", 3, 20);
        
        // Criando EmployeeSimpleView para os campos client e collaborator do ProjectView
        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente Exemplo", 1L);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador Exemplo", 2L);

        // Criando o ProjectView com client, collaborator e briefingType
        ProjectView project1 = new ProjectView(1L, "Projeto A", "Em andamento", client, collaborator, "Tipo de Briefing", LocalDate.now());

        // Criando o BriefingView
        BriefingView briefing1 = new BriefingView(1L, null, null, null, null, null, null, null, null, null);

        // Criando as instâncias de BPrintedsDetailedView
        BPrintedsDetailedView detailedView1 = new BPrintedsDetailedView(printed1, project1, briefing1);
        BPrintedsDetailedView detailedView2 = new BPrintedsDetailedView(printed1, project1, briefing1);
        BPrintedsDetailedView detailedView3 = new BPrintedsDetailedView(printed2, project1, briefing1);

        // Act & Assert
        assertEquals(detailedView1, detailedView2); // devem ser iguais
        assertNotEquals(detailedView1, detailedView3); // não devem ser iguais
        assertEquals(detailedView1.hashCode(), detailedView2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(detailedView1.hashCode(), detailedView3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
