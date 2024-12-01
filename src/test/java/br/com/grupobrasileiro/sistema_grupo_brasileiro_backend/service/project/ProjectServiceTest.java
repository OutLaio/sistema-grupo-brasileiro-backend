package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.AssignCollaboratorForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidProfileException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

@DisplayName("ProjectService Unit Tests")
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectFormMapper projectFormMapper;

    
    @Mock
    private ProjectViewMapper projectViewMapper;
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private BAgencyBoardDetailedViewMapper bAgencyBoardDetailedViewMapper;

    @Mock
    private BSignpostDetailedViewMapper bSignpostDetailedViewMapper;

    
    @InjectMocks
    private ProjectService projectService;

    private final Faker faker = new Faker();

    public ProjectServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Must register a projectForm successfully")
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
    @DisplayName("Should throw exception when client is not found when registering a projectForm")
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
    @DisplayName("Should throw exception when projectForm not found when assigning collaborator")
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
    @DisplayName("Should throw exception when collaborator is not found when assigning collaborator")
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
    @DisplayName("Should throw exception when projectForm not foundo")
    void shouldThrowExceptionWhenProjectNotFound() {
        Long projectId = faker.number().randomNumber();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, 
            () -> projectService.setFinished(projectId), 
            () -> "Deveria lançar EntityNotFoundException quando o projeto não for encontrado"
        );
    }

    @Test
    @DisplayName("Should update the projectForm status to COMPLETED successfully")
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
    @DisplayName("Should throw exception when employee profile is not suitable")
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
    @DisplayName("Should update projectForm status to IN_PRODUCTION when hasConfection is true")
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
    @DisplayName("Should update projectForm status to COMPLETED when hasConfection is false")
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
    @DisplayName("Must update projectForm status to COMPLETED upon completion")
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
    @DisplayName("Should throw exception when collaborator is not found")
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
    @DisplayName("Should throw exception when employee profile is not suitable")
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
    @DisplayName("Must assign collaborator and update status to IN_PROGRESS when projectForm is in TO_DO")
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
    @DisplayName("Should update projectForm status to COMPLETED when hasConfection is false")
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
    @DisplayName("Must update projectForm status to STAND_BY")
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
    @DisplayName("Should update projectForm status to IN_PROGRESS when status is TO_DO and a collaborator is assigned")
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
    
    
   

    @Test
    @DisplayName("Should throw exception when projectForm is not found by ID")
    void shouldThrowEntityNotFoundExceptionWhenProjectNotFoundById() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
            () -> projectService.getById(projectId),
            "Project not found for id: " + projectId
        );
    }

    @Test
    @DisplayName("Should throw exception when briefing field is null")
    void shouldThrowNullPointerExceptionWhenBriefingFieldIsNull() {
        Long projectId = 1L;
        Project project = new Project();
        Briefing briefing = new Briefing();
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("PLACA DE ITINERÁRIOS");
        briefing.setBriefingType(briefingType);
        project.setBriefing(briefing);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        assertThrows(NullPointerException.class,
            () -> projectService.getById(projectId),
            "Error retrieving the briefing: The field agencyBoard is null"
        );
    }

    @Test
    @DisplayName("Should throw exception when briefing type is not valid")
    void shouldThrowIllegalArgumentExceptionWhenBriefingTypeIsInvalid() {
        Long projectId = 1L;
        Project project = new Project();
        Briefing briefing = new Briefing();
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription("INVALID_TYPE");
        briefing.setBriefingType(briefingType);
        project.setBriefing(briefing);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        assertThrows(IllegalArgumentException.class,
            () -> projectService.getById(projectId),
            "Error retrieving the briefing: The projectForm briefing type is not valid"
        );
    }
    @Test
    @DisplayName("Deve retornar todos os projetos quando o perfil do usuário não for 3")
    void shouldReturnAllProjectsForNonEmployeeUser() {
        Long userId = 1L;
        User user = new User();
        Profile profile = new Profile();
        profile.setId(2L); // Perfil que não é '3'
        user.setProfile(profile);

        Project project1 = new Project();
        project1.setTitle("Projeto 1");
        Project project2 = new Project();
        project2.setTitle("Projeto 2");

        Set<Project> allProjects = new HashSet<>();
        allProjects.add(project1);
        allProjects.add(project2);

        List<Project> allProjectsList = new ArrayList<>(allProjects);  
        
        // Mocking
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(projectRepository.findAll()).thenReturn(allProjectsList);  // Agora retornando uma List
        when(projectViewMapper.map(any(Project.class))).thenReturn(new ProjectView(1L, "Projeto", "TO_DO", null, null, null, LocalDate.now()));

        // Chamada do método
        Set<ProjectView> projectViews = projectService.getAll(userId);

        // Verificações
        assertNotNull(projectViews, "O resultado não pode ser nulo");
        assertEquals(2, projectViews.size(), "Deveria retornar 2 projetos");
        verify(userRepository).getReferenceById(userId);
        verify(projectRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar apenas os projetos do colaborador quando o perfil for 3")
    void shouldReturnOwnedProjectsForEmployeeUser() {
        Long userId = 1L;
        User user = new User();
        Profile profile = new Profile();
        profile.setId(3L); // Perfil de colaborador
        user.setProfile(profile);

        Employee employee = new Employee();
        Project project1 = new Project();
        project1.setTitle("Projeto 1");
        employee.setOwnedProjects(new HashSet<>());
        employee.getOwnedProjects().add(project1);
        user.setEmployee(employee);

        // Mocking
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(projectViewMapper.map(any(Project.class))).thenReturn(new ProjectView(1L, "Projeto 1", "IN_PROGRESS", null, null, null, LocalDate.now()));

        // Chamada do método
        Set<ProjectView> projectViews = projectService.getAll(userId);

        // Verificações
        assertNotNull(projectViews, "O resultado não pode ser nulo");
        assertEquals(1, projectViews.size(), "Deveria retornar 1 projeto");
        verify(userRepository).getReferenceById(userId);
        verify(projectRepository, never()).findAll(); // Verifica que o método findAll não foi chamado
    }
}

