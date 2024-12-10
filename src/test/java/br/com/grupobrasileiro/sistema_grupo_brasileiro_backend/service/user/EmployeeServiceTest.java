package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form.EmployeeFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeViewMapper employeeViewMapper;

    @Mock
    private EmployeeFormMapper employeeFormMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Should successfully add a new employee and return a correct EmployeeView")
    void shouldAddEmployeeSuccessfully() {
        // Arrange
        EmployeeForm employeeForm = mock(EmployeeForm.class);
        User user = mock(User.class);
        Employee employee = new Employee();

        // Simulação do mapeamento do formulário para a entidade Employee
        when(employeeFormMapper.map(employeeForm)).thenReturn(employee);

        // Simulação do salvamento da entidade no repositório
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Dados simulados para UserView e EmployeeView
        Long userId = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        ProfileView profileView = new ProfileView(faker.number().randomNumber(), faker.job().position());
        UserView userView = new UserView(userId, email, profileView);

        // Dados para EmployeeView simulados e esperados
        EmployeeView expectedEmployeeView = new EmployeeView(
            faker.number().randomNumber(),  // ID do funcionário
            userView,                       // userView associado
            faker.name().firstName(),       // Nome
            faker.name().lastName(),        // Sobrenome
            faker.phoneNumber().phoneNumber(), // Telefone
            faker.company().industry(),     // Setor
            faker.job().title(),            // Ocupação
            faker.company().name(),         // Agência
            faker.number().digits(6),
            faker.number().randomNumber()   // Avatar
        );

        // Simulação do mapeamento da entidade Employee para EmployeeView
        when(employeeViewMapper.map(employee)).thenReturn(expectedEmployeeView);

        // Act
        EmployeeView result = employeeService.addEmployee(employeeForm, user);

        // Assert
        assertNotNull(result, "Expected EmployeeView to be not null");
        assertEquals(expectedEmployeeView.user().id(), result.user().id(), "User ID mismatch");
        assertEquals(expectedEmployeeView.user().email(), result.user().email(), "User email mismatch");
        assertEquals(expectedEmployeeView.name(), result.name(), "Name mismatch");
        assertEquals(expectedEmployeeView.lastname(), result.lastname(), "Last name mismatch");
        assertEquals(expectedEmployeeView.phoneNumber(), result.phoneNumber(), "Phone number mismatch");
        assertEquals(expectedEmployeeView.sector(), result.sector(), "Sector mismatch");
        assertEquals(expectedEmployeeView.occupation(), result.occupation(), "Occupation mismatch");
        assertEquals(expectedEmployeeView.agency(), result.agency(), "Agency mismatch");
        assertEquals(expectedEmployeeView.avatar(), result.avatar(), "Avatar ID mismatch");
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when employee not found by ID")
    void shouldThrowEntityNotFoundExceptionWhenEmployeeNotFound() {
        Long invalidId = faker.number().randomNumber();
        when(employeeRepository.findById(invalidId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.getEmployeeById(invalidId));

        assertEquals("Funcionário não encontrado com id:: " + invalidId, exception.getMessage());
        verify(employeeRepository).findById(invalidId);
    }
    
    @Test
    @DisplayName("Should get an employee by ID successfully")
    void shouldGetEmployeeByIdSuccessfully() {
        Long employeeId = faker.number().randomNumber();
        Employee employee = mock(Employee.class);
        EmployeeView employeeView = mock(EmployeeView.class);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeViewMapper.map(employee)).thenReturn(employeeView);

        EmployeeView result = employeeService.getEmployeeById(employeeId);

        assertNotNull(result);
        assertEquals(employeeView, result);
    }

    @Test
    @DisplayName("Should update an existing employee successfully")
    void shouldUpdateEmployeeSuccessfully() {
        Long employeeId = faker.number().randomNumber();
        EmployeeForm form = mock(EmployeeForm.class);
        Employee employee = new Employee();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        EmployeeView employeeView = mock(EmployeeView.class);
        when(employeeViewMapper.map(employee)).thenReturn(employeeView);

        EmployeeView result = employeeService.updateEmployee(employeeId, form);

        assertNotNull(result);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Should return a page of collaborators successfully")
    void shouldReturnPageOfCollaboratorsSuccessfully() {
        Pageable pageable = Pageable.unpaged();

        Employee employee1 = new Employee(1L, faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(), 
                faker.company().industry(), faker.job().title(), faker.company().name(), faker.number().digits(6), faker.number().randomNumber(), new User(), null, null, null);
        
        Employee employee2 = new Employee(2L, faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().phoneNumber(), 
                faker.company().industry(), faker.job().title(), faker.company().name(), faker.number().digits(6), faker.number().randomNumber(), new User(), null, null, null);

        List<Employee> employees = List.of(employee1, employee2);
        Page<Employee> employeePage = new PageImpl<>(employees);

        EmployeeView employeeView1 = mock(EmployeeView.class);
        EmployeeView employeeView2 = mock(EmployeeView.class);
        List<EmployeeView> employeeViews = List.of(employeeView1, employeeView2);

        when(employeeRepository.findAllCollaborators(pageable)).thenReturn(employeePage);
        when(employeeViewMapper.map(employee1)).thenReturn(employeeView1);
        when(employeeViewMapper.map(employee2)).thenReturn(employeeView2);

        Page<EmployeeView> result = employeeService.getAllCollaborators(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertIterableEquals(employeeViews, result.getContent());
    }

    @Test
    @DisplayName("Should return empty page when no collaborators found")
    void shouldReturnEmptyPageWhenNoCollaboratorsFound() {
        Pageable pageable = mock(Pageable.class);
        when(employeeRepository.findAllCollaborators(pageable)).thenReturn(Page.empty());

        Page<EmployeeView> result = employeeService.getAllCollaborators(pageable);

        assertTrue(result.isEmpty());
    }
}
