package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.BStickerDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.BStickerView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerInformationTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BStickerDetailedViewTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        // Inicializa o Faker para gerar dados aleatórios
        faker = new Faker();
    }

    @Test
    void testCreateBStickerDetailedView() {
        // Gerando dados falsos com o Faker para o BStickerView
        Long stickerId = faker.number().randomNumber();
        String stickerSector = faker.commerce().department();
        String stickerObservations = faker.lorem().sentence();

        // Criando o objeto BStickerView
        BStickerView bStickerView = new BStickerView(stickerId,
                new StickerTypeView(stickerId, faker.commerce().productName()),
                new StickerInformationTypeView(faker.number().randomNumber(), faker.commerce().department()),
                stickerSector,
                stickerObservations);

        // Gerando dados falsos para o ProjectView
        Long projectId = faker.number().randomNumber();
        String projectTitle = faker.company().name();
        String projectStatus = faker.company().industry();

        // Usando EmployeeSimpleView para cliente e colaborador
        EmployeeSimpleView client = new EmployeeSimpleView(faker.number().randomNumber(), faker.name().fullName(), faker.number().randomNumber());
        EmployeeSimpleView collaborator = new EmployeeSimpleView(faker.number().randomNumber(), faker.name().fullName(), faker.number().randomNumber());

        String briefingType = faker.commerce().productName();

        ProjectView projectView = new ProjectView(projectId, projectTitle, projectStatus, client, collaborator, briefingType);

        // Gerando dados falsos para o BriefingView
        Long briefingId = faker.number().randomNumber();
        String briefingDescription = faker.lorem().sentence();
        LocalDate startTime = LocalDate.now();
        LocalDate expectedTime = startTime.plusDays(10);
        LocalDate finishTime = startTime.plusDays(20);

        // Gerando dados para MeasurementsView
        BigDecimal height = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10));
        BigDecimal length = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10));
        MeasurementsView measurementsView = new MeasurementsView(height, length);

        // Gerando dados para CompaniesBriefingsView
        Set<CompanyView> companyViews = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            companyViews.add(new CompanyView(faker.number().randomNumber(), faker.company().name()));
        }

        CompaniesBriefingsView companiesBriefingsView = new CompaniesBriefingsView(companyViews);

        // Gerando dados para VersionView
        Set<VersionView> versions = new HashSet<>();
        Long versionId = faker.number().randomNumber();
        Integer versionNumber = faker.number().numberBetween(1, 10);
        String productLink = faker.internet().url();
        Boolean clientApprove = faker.bool().bool();
        Boolean supervisorApprove = faker.bool().bool();
        String feedback = faker.lorem().sentence();
        
        VersionView versionView = new VersionView(versionId, versionNumber, productLink, clientApprove, supervisorApprove, feedback);
        versions.add(versionView);

        BriefingView briefingView = new BriefingView(briefingId,
                new BriefingTypeView(faker.number().randomNumber(), faker.commerce().productName()),
                startTime, expectedTime, finishTime, briefingDescription,
                measurementsView, companiesBriefingsView, faker.lorem().sentence(), versions);

        // Criando o BStickerDetailedView com os dados gerados
        BStickerDetailedView bStickerDetailedView = new BStickerDetailedView(bStickerView, projectView, briefingView);

        // Verificando se os dados do BStickerDetailedView estão corretos
        assertNotNull(bStickerDetailedView);
        assertEquals(bStickerView, bStickerDetailedView.sticker());
        assertEquals(projectView, bStickerDetailedView.project());
        assertEquals(briefingView, bStickerDetailedView.briefing());
    }

    @Test
    void testCreateBStickerDetailedViewWithNullValues() {
        // Testa o comportamento quando o BStickerDetailedView é criado com valores nulos
        BStickerDetailedView bStickerDetailedView = new BStickerDetailedView(null, null, null);

        // Verifica se o objeto BStickerDetailedView é criado com valores nulos
        assertNotNull(bStickerDetailedView);
        assertNull(bStickerDetailedView.sticker());
        assertNull(bStickerDetailedView.project());
        assertNull(bStickerDetailedView.briefing());
    }

    @Test
    void testCreateBStickerDetailedViewWithFakeData() {
        // Gerando dados falsos para o BStickerView
        Long stickerId = faker.number().randomNumber();
        String stickerSector = faker.commerce().department();
        String stickerObservations = faker.lorem().sentence();

        // Criando o objeto BStickerView
        BStickerView bStickerView = new BStickerView(stickerId,
                new StickerTypeView(stickerId, faker.commerce().productName()),
                new StickerInformationTypeView(faker.number().randomNumber(), faker.commerce().department()),
                stickerSector,
                stickerObservations);

        // Gerando dados falsos para o ProjectView
        Long projectId = faker.number().randomNumber();
        String projectTitle = faker.company().name();
        String projectStatus = faker.company().industry();

        // Usando EmployeeSimpleView para cliente e colaborador
        EmployeeSimpleView client = new EmployeeSimpleView(faker.number().randomNumber(), faker.name().fullName(), faker.number().randomNumber());
        EmployeeSimpleView collaborator = new EmployeeSimpleView(faker.number().randomNumber(), faker.name().fullName(), faker.number().randomNumber());

        String briefingType = faker.commerce().productName();
        
        ProjectView projectView = new ProjectView(projectId, projectTitle, projectStatus, client, collaborator, briefingType);

        // Gerando dados falsos para o BriefingView
        Long briefingId = faker.number().randomNumber();
        String briefingDescription = faker.lorem().sentence();
        LocalDate startTime = LocalDate.now();
        LocalDate expectedTime = startTime.plusDays(10);
        LocalDate finishTime = startTime.plusDays(20);

        // Gerando dados para MeasurementsView
        BigDecimal height = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10));
        BigDecimal length = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10));
        MeasurementsView measurementsView = new MeasurementsView(height, length);

        // Gerando dados para CompaniesBriefingsView
        Set<CompanyView> companyViews = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            companyViews.add(new CompanyView(faker.number().randomNumber(), faker.company().name()));
        }

        CompaniesBriefingsView companiesBriefingsView = new CompaniesBriefingsView(companyViews);

        Set<VersionView> versions = new HashSet<>();

        // Gerando dados para VersionView
        Long versionId = faker.number().randomNumber();
        Integer versionNumber = faker.number().numberBetween(1, 10);
        String productLink = faker.internet().url();
        Boolean clientApprove = faker.bool().bool();
        Boolean supervisorApprove = faker.bool().bool();
        String feedback = faker.lorem().sentence();

        VersionView versionView = new VersionView(versionId, versionNumber, productLink, clientApprove, supervisorApprove, feedback);
        versions.add(versionView);

        BriefingView briefingView = new BriefingView(briefingId,
                new BriefingTypeView(faker.number().randomNumber(), faker.commerce().productName()),
                startTime, expectedTime, finishTime, briefingDescription,
                measurementsView, companiesBriefingsView, faker.lorem().sentence(), versions);

        // Criando o BStickerDetailedView com os dados gerados
        BStickerDetailedView bStickerDetailedView = new BStickerDetailedView(bStickerView, projectView, briefingView);

        // Verificando se todos os dados foram corretamente atribuídos
        assertEquals(stickerId, bStickerDetailedView.sticker().id());
        assertEquals(projectId, bStickerDetailedView.project().id());
        assertEquals(briefingId, bStickerDetailedView.briefing().id());
    }
}

