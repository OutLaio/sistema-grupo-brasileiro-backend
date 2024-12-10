package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.VersionFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.VersionViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
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

    @Mock
    private VersionFormMapper versionFormMapper;

    @Mock
    private VersionViewMapper versionViewMapper;

    @Mock
    private DialogBoxService dialogBoxService;

    @InjectMocks
    private VersionService versionService;

    private final Faker faker = new Faker();

    public VersionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Must approve the version by supervisor and update projectForm status to IN_PROGRESS if not approved")
    void shouldApproveVersionBySupervisorAndUpdateProjectStatusToInProgressIfNotApproved() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();
        String feedback = faker.lorem().sentence();

        // Criando o projeto com o status IN_PROGRESS
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.IN_PROGRESS.toString());

        // Criando o briefing e associando ao projeto
        Briefing briefing = new Briefing();
        project.setBriefing(briefing);  // Associando o briefing ao projeto

        // Criando a versão com supervisorApprove como false
        Version version = new Version();
        version.setSupervisorApprove(false); // Não aprovado inicialmente
        version.setNumVersion(1);  // Definindo o número da versão
        version.setFeedback(feedback); // Atualizando feedback

        // Criando o form de aprovação com feedback
        ApproveForm form = new ApproveForm(versionId, false, feedback);

        // Mockando as interações com os repositórios
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.of(version));

        // Mockando o comportamento do mapeamento de versão
        VersionView view = new VersionView(null, null, null, null, null, null);
        when(versionViewMapper.map(version)).thenReturn(view);

        // Mockando o serviço de mensagens
        when(dialogBoxService.createMessage(any())).thenReturn(null);

        // Executando a aprovação do supervisor
        versionService.supervisorApprove(projectId, form);

        // Verificando que o status do projeto não foi alterado para IN_PROGRESS
        assertEquals(ProjectStatusEnum.IN_PROGRESS.toString(), project.getStatus(),
                () -> "O status do projeto deve ser IN_PROGRESS quando a versão não for aprovada pelo supervisor"
        );

        // Verificando se o feedback foi corretamente atualizado
        assertEquals(feedback, version.getFeedback(),
                () -> "O feedback da versão deve ser atualizado"
        );

        // Verificando que o método de sincronização de status foi chamado
        verify(projectRepository).save(project);  // Verifica se o projeto foi salvo
        verify(versionRepository).save(version);  // Verifica se a versão foi salva
        verify(dialogBoxService).createMessage(any());  // Verifica se a mensagem foi criada
    }

    @Test
    @DisplayName("Should throw exception when projectForm is not found when approving version by supervisor")
    void shouldThrowExceptionWhenProjectNotFoundForSupervisorApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        ApproveForm form = new ApproveForm(versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.supervisorApprove(projectId, form),
            () -> "Deveria lançar EntityNotFoundException quando o projeto não for encontrado"
        );
    }

    @Test
    @DisplayName("Should throw exception when version not found when approving version by supervisor")
    void shouldThrowExceptionWhenVersionNotFoundForSupervisorApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        Project project = new Project();

        ApproveForm form = new ApproveForm(versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.supervisorApprove(projectId, form),
            () -> "Deveria lançar EntityNotFoundException quando a versão não for encontrada"
        );
    }

    @Test
    @DisplayName("Should throw exception when projectForm is not found when approving version by client")
    void shouldThrowExceptionWhenProjectNotFoundForClientApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        ApproveForm form = new ApproveForm(versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.clientApprove(projectId, form),
            () -> "Deveria lançar EntityNotFoundException quando o projeto não for encontrado"
        );
    }

    @Test
    @DisplayName("Should throw exception when version not found when approving version by client")
    void shouldThrowExceptionWhenVersionNotFoundForClientApproval() {
        Long projectId = faker.number().randomNumber();
        Long versionId = faker.number().randomNumber();

        Project project = new Project();

        ApproveForm form = new ApproveForm(versionId, true, null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(versionRepository.findById(versionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> versionService.clientApprove(projectId, form),
            () -> "Deveria lançar EntityNotFoundException quando a versão não for encontrada"
        );
    }

    @Test
    @DisplayName("Should throw exception when projectForm not found when creating a new version")
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