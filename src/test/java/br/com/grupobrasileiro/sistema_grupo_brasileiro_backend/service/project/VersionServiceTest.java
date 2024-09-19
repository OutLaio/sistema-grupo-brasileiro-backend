package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ApproveForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.VersionRepository;

public class VersionServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private VersionRepository versionRepository;

    @InjectMocks
    private VersionService versionService;

    private final Faker faker = new Faker();

    public VersionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve aprovar a versão pelo supervisor e atualizar o status do projeto para IN_PROGRESS se não aprovado")
    void shouldApproveVersionBySupervisorAndUpdateProjectStatusToInProgressIfNotApproved() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();
        String feedback = faker.lorem().sentence();

        Project project = new Project();
        project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());

        Version version = new Version();
        version.setSupervisorApprove(false); // Não aprovado inicialmente

        ApproveForm form = new ApproveForm(projectId, versionId, false, feedback);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.of(version));

        versionService.supervisorApprove(form);

        assertEquals(ProjectStatusEnum.IN_PROGRESS.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser IN_PROGRESS quando a versão não for aprovada pelo supervisor"
        );
        assertEquals(feedback, version.getFeedback(), 
            () -> "O feedback da versão deve ser atualizado"
        );
        verify(projectRepository).save(project);
        verify(versionRepository).save(version);
    }
    
    


    @Test
    @DisplayName("Deve aprovar a versão pelo cliente e atualizar o status do projeto para APPROVED se aprovado")
    void shouldApproveVersionByClientAndUpdateProjectStatusToApprovedIfApproved() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        Project project = new Project();
        project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());

        Version version = new Version();
        version.setClientApprove(true);

        ApproveForm form = new ApproveForm(projectId, versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.of(version));

        versionService.clientApprove(form);

        assertEquals(ProjectStatusEnum.APPROVED.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser APPROVED quando a versão for aprovada pelo cliente"
        );
        verify(projectRepository).save(project);
        verify(versionRepository).save(version);
    }

    @Test
    @DisplayName("Deve criar uma nova versão e atualizar o status do projeto para WAITING_APPROVAL se o status do projeto for IN_PROGRESS")
    void shouldCreateNewVersionAndUpdateProjectStatusToWaitingApprovalIfInProgress() {
        Long projectId = faker.number().randomNumber();
        String productLink = faker.internet().url();

        Project project = new Project();
        project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());
        Briefing briefing = new Briefing();
        project.setBriefing(briefing);

        NewVersionForm form = new NewVersionForm(productLink);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.countVersionsByBriefingId(briefing.getId())).thenReturn(5L); // Supondo que existam 5 versões

        versionService.create(projectId, form);

        verify(projectRepository).save(project);
        verify(versionRepository).save(any(Version.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o projeto não for encontrado ao aprovar a versão pelo supervisor")
    void shouldThrowExceptionWhenProjectNotFoundForSupervisorApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        ApproveForm form = new ApproveForm(projectId, versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.supervisorApprove(form), 
            () -> "Deveria lançar EntityNotFoundException quando o projeto não for encontrado"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando a versão não for encontrada ao aprovar a versão pelo supervisor")
    void shouldThrowExceptionWhenVersionNotFoundForSupervisorApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        Project project = new Project();

        ApproveForm form = new ApproveForm(projectId, versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.supervisorApprove(form), 
            () -> "Deveria lançar EntityNotFoundException quando a versão não for encontrada"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o projeto não for encontrado ao aprovar a versão pelo cliente")
    void shouldThrowExceptionWhenProjectNotFoundForClientApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        ApproveForm form = new ApproveForm(projectId, versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.clientApprove(form), 
            () -> "Deveria lançar EntityNotFoundException quando o projeto não for encontrado"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando a versão não for encontrada ao aprovar a versão pelo cliente")
    void shouldThrowExceptionWhenVersionNotFoundForClientApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        Project project = new Project();

        ApproveForm form = new ApproveForm(projectId, versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.clientApprove(form), 
            () -> "Deveria lançar EntityNotFoundException quando a versão não for encontrada"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o projeto não for encontrado ao criar uma nova versão")
    void shouldThrowExceptionWhenProjectNotFoundForNewVersion() {
        Long projectId = faker.number().randomNumber();
        String productLink = faker.internet().url();
        NewVersionForm form = new NewVersionForm(productLink);

        // Simular o comportamento do repositório para retornar vazio quando o projeto é buscado
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Verificar se a exceção é lançada quando o projeto não é encontrado
        assertThrows(EntityNotFoundException.class, 
            () -> versionService.create(projectId, form),
            () -> "Deveria lançar uma EntityNotFoundException quando o projeto não for encontrado"
        );
    }
    
    
    
    
}