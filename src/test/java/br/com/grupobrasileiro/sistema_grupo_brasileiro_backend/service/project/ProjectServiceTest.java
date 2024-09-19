package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidProfileException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;

@DisplayName("ProjectService Unit Tests")
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectFormMapper projectFormMapper;

    @InjectMocks
    private ProjectService projectService;

    private final Faker faker = new Faker();

    public ProjectServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve registrar um projeto com sucesso")
    void shouldRegisterProjectSuccessfully() {
        Long clientId = faker.number().randomNumber();
        String title = faker.lorem().word();
        ProjectStatusEnum status = ProjectStatusEnum.TO_DO;
        ProjectForm projectForm = new ProjectForm(clientId, title, status);

        Employee client = new Employee();
        client.setId(clientId);
        client.setName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        client.setPhoneNumber(faker.phoneNumber().phoneNumber());
        client.setSector(faker.company().industry());
        client.setOccupation(faker.job().title());
        client.setAgency(faker.company().name());
        client.setAvatar(faker.number().randomNumber());
        
        Project project = new Project();
        project.setTitle(title);
        project.setStatus(status.toString());
        project.setClient(client);

        when(employeeRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(projectFormMapper.map(projectForm)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project registeredProject = projectService.register(projectForm);

        // Verificação dos atributos específicos
        assertNotNull(registeredProject, () -> "O projeto registrado não pode ser nulo");
        assertEquals(client.getId(), registeredProject.getClient().getId(), () -> "O ID do cliente está incorreto");
        assertEquals(client.getName(), registeredProject.getClient().getName(), () -> "O nome do cliente está incorreto");
        assertEquals(client.getLastName(), registeredProject.getClient().getLastName(), () -> "O sobrenome do cliente está incorreto");
        assertEquals(client.getPhoneNumber(), registeredProject.getClient().getPhoneNumber(), () -> "O telefone do cliente está incorreto");
        assertEquals(client.getSector(), registeredProject.getClient().getSector(), () -> "O setor do cliente está incorreto");
        assertEquals(client.getOccupation(), registeredProject.getClient().getOccupation(), () -> "A ocupação do cliente está incorreta");
        assertEquals(client.getAgency(), registeredProject.getClient().getAgency(), () -> "A agência do cliente está incorreta");
        assertEquals(client.getAvatar(), registeredProject.getClient().getAvatar(), () -> "O avatar do cliente está incorreto");
        assertEquals(title, registeredProject.getTitle(), () -> "O título do projeto registrado está incorreto");
        assertEquals(status.toString(), registeredProject.getStatus(), () -> "O status do projeto registrado está incorreto");
        verify(projectRepository).save(project);
    }


    @Test
    @DisplayName("Deve lançar exceção quando o cliente não for encontrado ao registrar um projeto")
    void shouldThrowEntityNotFoundExceptionWhenClientNotFound() {
        Long clientId = faker.number().randomNumber();
        String title = faker.lorem().word();
        ProjectStatusEnum status = ProjectStatusEnum.TO_DO;
        ProjectForm projectForm = new ProjectForm(clientId, title, status);

        when(employeeRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> projectService.register(projectForm), 
            () -> "Client not found with ID: " + clientId
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o projeto não for encontrado ao atribuir colaborador")
    void shouldThrowEntityNotFoundExceptionWhenProjectNotFound() {
        Long projectId = 1L;
        Long collaboratorId = 2L;
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> projectService.assignCollaborator(projectId, form), 
            () -> "Project nof found for id: " + projectId
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o colaborador não for encontrado ao atribuir colaborador")
    void shouldThrowEntityNotFoundExceptionWhenEmployeeNotFound() {
        Long projectId = 1L;
        Long collaboratorId = 2L;
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        Project project = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(collaboratorId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> projectService.assignCollaborator(projectId, form), 
            () -> "Employee not found for id: " + collaboratorId
        );
    }


    @Test
    @DisplayName("Deve lançar exceção quando o projeto não for encontrado")
    void shouldThrowExceptionWhenProjectNotFound() {
        Long projectId = faker.number().randomNumber();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> projectService.setFinished(projectId), 
            () -> "Deveria lançar EntityNotFoundException quando o projeto não for encontrado"
        );
    }

    @Test
    @DisplayName("Deve atualizar o status do projeto para COMPLETED com sucesso")
    void shouldSetProjectStatusToCompletedSuccessfully() {
        Long projectId = faker.number().randomNumber();
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString()); // Status inicial

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setFinished(projectId);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.COMPLETED.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para COMPLETED"
        );
    }
    @Test
    @DisplayName("Deve lançar exceção quando o perfil do colaborador não for adequado")
    void shouldThrowInvalidProfileExceptionWhenEmployeeProfileIsInvalid() {
        Long projectId = 1L;
        Long collaboratorId = 2L;
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        Project project = new Project();
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setId(1L); // Perfil inválido para o colaborador
        employee.setUser(new User());
        employee.getUser().setProfile(profile);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(collaboratorId)).thenReturn(Optional.of(employee));

        assertThrows(InvalidProfileException.class, 
            () -> projectService.assignCollaborator(projectId, form), 
            () -> "The employee is not a Collaborator"
        );
    }
    @Test
    @DisplayName("Deve atualizar status do projeto para IN_PRODUCTION quando hasConfection for true")
    void shouldSetProjectStatusToInProductionWhenHasConfectionIsTrue() {
        Long projectId = 1L;
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setHasConfection(projectId, true);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.IN_PRODUCTION.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para IN_PRODUCTION"
        );
    }

    @Test
    @DisplayName("Deve atualizar status do projeto para COMPLETED quando hasConfection for false")
    void shouldSetProjectStatusToCompletedWhenHasConfectionIsFalse() {
        Long projectId = 1L;
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setHasConfection(projectId, false);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.COMPLETED.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para COMPLETED"
        );
    }

    @Test
    @DisplayName("Deve atualizar status do projeto para COMPLETED ao finalizar")
    void shouldSetProjectStatusToCompletedWhenFinished() {
        Long projectId = 1L;
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setFinished(projectId);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.COMPLETED.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para COMPLETED"
        );
    }



    @Test
    @DisplayName("Deve lançar exceção quando o colaborador não for encontrado")
    void shouldThrowExceptionWhenEmployeeNotFound() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        Project project = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(collaboratorId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> projectService.assignCollaborator(projectId, form), 
            () -> "Deveria lançar EntityNotFoundException quando o colaborador não for encontrado"
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o perfil do colaborador não for adequado")
    void shouldThrowExceptionWhenEmployeeProfileIsInvalid() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        Project project = new Project();
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setId(1L);
        employee.setUser(new User());
        employee.getUser().setProfile(profile);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(collaboratorId)).thenReturn(Optional.of(employee));

        assertThrows(InvalidProfileException.class, 
            () -> projectService.assignCollaborator(projectId, form), 
            () -> "Deveria lançar InvalidProfileException quando o perfil do colaborador não for adequado"
        );
    }

    @Test
    @DisplayName("Deve atribuir colaborador e atualizar status para IN_PROGRESS quando o projeto estiver em TO_DO")
    void shouldAssignCollaboratorAndUpdateStatus() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setId(2L);
        employee.setUser(new User());
        employee.getUser().setProfile(profile);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(collaboratorId)).thenReturn(Optional.of(employee));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.assignCollaborator(projectId, form);

        verify(projectRepository).save(project);
        verify(employeeRepository).findById(collaboratorId);
    }

    @Test
    @DisplayName("Deve atualizar status do projeto para IN_PRODUCTION quando hasConfection for true")
    void shouldSetProjectStatusToInProduction() {
        Long projectId = faker.number().randomNumber();
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setHasConfection(projectId, true);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.IN_PRODUCTION.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para IN_PRODUCTION"
        );
    }

    @Test
    @DisplayName("Deve atualizar status do projeto para COMPLETED quando hasConfection for false")
    void shouldSetProjectStatusToCompleted() {
        Long projectId = faker.number().randomNumber();
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setHasConfection(projectId, false);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.COMPLETED.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para COMPLETED"
        );
    }

    @Test
    @DisplayName("Deve atualizar status do projeto para STAND_BY")
    void shouldSetProjectStatusToStandBy() {
        Long projectId = faker.number().randomNumber();
        Project project = new Project();

        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.setStandby(projectId);

        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.STAND_BY.toString(), project.getStatus());
    }
    
    @Test
    @DisplayName("Deve atualizar o status do projeto para IN_PROGRESS quando o status é TO_DO e um colaborador é atribuído")
    void shouldUpdateProjectStatusToInProgressWhenStatusIsToDo() {
        Long projectId = 1L;
        Long collaboratorId = 2L;
        AssignCollaboratorForm form = new AssignCollaboratorForm(collaboratorId);

        // Configurando o projeto com status TO_DO
        Project project = new Project();
        project.setStatus(ProjectStatusEnum.TO_DO.toString());

        // Configurando o colaborador
        Employee employee = new Employee();
        Profile profile = new Profile();
        profile.setId(2L); // Perfil de colaborador
        employee.setUser(new User());
        employee.getUser().setProfile(profile);

        // Mocking os repositórios
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(employeeRepository.findById(collaboratorId)).thenReturn(Optional.of(employee));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chamada do método
        projectService.assignCollaborator(projectId, form);

        // Verificações
        verify(projectRepository).save(project);
        assertEquals(ProjectStatusEnum.IN_PROGRESS.toString(), project.getStatus(), 
            () -> "O status do projeto deve ser atualizado para IN_PROGRESS"
        );
        assertEquals(employee, project.getCollaborator(), 
            () -> "O colaborador deve ser atribuído ao projeto"
        );
    }

}

