package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @DisplayName("Should successfully add a new employee with correct EmployeeView")
    void shouldAddEmployeeSuccessfully() {
        // Arrange
        EmployeeForm employeeForm = mock(EmployeeForm.class);
        User user = mock(User.class);

        // Criação de um Employee mockado
        Employee employee = new Employee();
        when(employeeFormMapper.map(any(EmployeeForm.class))).thenReturn(employee);

        // Criando o UserView
        Long userId = faker.number().randomNumber();
        String email = faker.internet().emailAddress();
        Long profileId = faker.number().randomNumber();
        String profileRole = faker.lorem().word();

        ProfileView profileView = new ProfileView(profileId, profileRole);
        UserView userView = new UserView(userId, email, profileView);

        // Criando o EmployeeView
        EmployeeView employeeView = new EmployeeView(
            faker.number().randomNumber(),
            userView,
            faker.name().firstName(),
            faker.name().lastName(),
            faker.phoneNumber().phoneNumber(),
            faker.company().industry(),
            faker.job().title(),
            faker.company().name(),
            faker.number().randomNumber()
        );

        // Simular a chamada de mapeamento de EmployeeView
        when(employeeViewMapper.map(any(Employee.class))).thenReturn(employeeView);

        // Simular o comportamento do repositório
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act
        EmployeeView result = employeeService.addEmployee(employeeForm, user);

        // Assert
        assertNotNull(result, () -> "Expected employee view to be not null");
        assertEquals(employeeView.userView(), result.userView(), () -> "Expected UserView to match");
        assertEquals(employeeView.userView().id(), result.userView().id(), () -> "Expected UserView ID to match");
        assertEquals(employeeView.userView().email(), result.userView().email(), () -> "Expected UserView email to match");
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when employee not found by ID")
    void shouldThrowEntityNotFoundExceptionWhenEmployeeNotFound() {
        // Arrange
        Long invalidId = faker.number().randomNumber();
        when(employeeRepository.findById(invalidId)).thenThrow(new EntityNotFoundException("Funcionário não encontrado"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> employeeService.getEmployeeById(invalidId),
                () -> "Expected EntityNotFoundException to be thrown");

        assertEquals("Funcionário não encontrado", exception.getMessage(), () -> "Expected correct exception message");
        verify(employeeRepository).findById(invalidId);
    }
    
    @Test
    @DisplayName("Should get an employee by ID successfully")
    void shouldGetEmployeeByIdSuccessfully() {
        // Arrange
        Long employeeId = faker.number().randomNumber();
        Employee employee = mock(Employee.class);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeViewMapper.map(employee)).thenReturn(mock(EmployeeView.class));

        // Act
        EmployeeView result = employeeService.getEmployeeById(employeeId);

        // Assert
        assertNotNull(result, () -> "Expected employee view to be not null");
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when employee is not found by ID")
    void shouldThrowExceptionWhenEmployeeNotFoundById() {
        // Arrange
        Long employeeId = faker.number().randomNumber();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> employeeService.getEmployeeById(employeeId), 
            () -> "Expected EntityNotFoundException when employee is not found");
    }
    
    @Test
    @DisplayName("Should update an existing employee successfully")
    void shouldUpdateEmployeeSuccessfully() {
        // Arrange
        Long employeeId = faker.number().randomNumber();
        EmployeeForm form = mock(EmployeeForm.class);
        Employee employee = mock(Employee.class);
        
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeViewMapper.map(employee)).thenReturn(mock(EmployeeView.class));

        // Act
        EmployeeView result = employeeService.updateEmployee(employeeId, form);

        // Assert
        verify(employeeRepository).save(employee);
        assertNotNull(result, () -> "Expected updated employee view to be not null");
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when updating non-existent employee")
    void shouldThrowExceptionWhenUpdatingNonExistentEmployee() {
        // Arrange
        Long employeeId = faker.number().randomNumber();
        EmployeeForm form = mock(EmployeeForm.class);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, 
            () -> employeeService.updateEmployee(employeeId, form), 
            () -> "Expected EntityNotFoundException when employee is not found");
    }

    @Test
    @DisplayName("Should return a page of collaborators successfully")
    void shouldReturnPageOfCollaboratorsSuccessfully() {
        // Arrange
        Pageable pageable = Pageable.unpaged(); // Use Pageable.unpaged() para um exemplo simples

        // Use Faker para criar dados dinâmicos
        String name1 = faker.name().firstName();
        String lastname1 = faker.name().lastName();
        String phoneNumber1 = faker.phoneNumber().phoneNumber();
        String sector1 = faker.company().industry();
        String occupation1 = faker.job().title();
        String agency1 = faker.company().name();
        Long avatar1 = faker.number().randomNumber();
        
        String name2 = faker.name().firstName();
        String lastname2 = faker.name().lastName();
        String phoneNumber2 = faker.phoneNumber().phoneNumber();
        String sector2 = faker.company().industry();
        String occupation2 = faker.job().title();
        String agency2 = faker.company().name();
        Long avatar2 = faker.number().randomNumber();

        // Crie Employees e EmployeeViews de exemplo
        Employee employee1 = new Employee(1L, name1, lastname1, phoneNumber1, sector1, occupation1, agency1, avatar1, new User(), null, null, null);
        Employee employee2 = new Employee(2L, name2, lastname2, phoneNumber2, sector2, occupation2, agency2, avatar2, new User(), null, null, null);
        List<Employee> employees = List.of(employee1, employee2);

        // Crie uma Page de Employees
        Page<Employee> employeePage = new PageImpl<>(employees);

        // Crie EmployeeViews esperados
        EmployeeView employeeView1 = new EmployeeView(1L, new UserView(1L, faker.internet().emailAddress(), null), name1, lastname1, phoneNumber1, sector1, occupation1, agency1, avatar1);
        EmployeeView employeeView2 = new EmployeeView(2L, new UserView(2L, faker.internet().emailAddress(), null), name2, lastname2, phoneNumber2, sector2, occupation2, agency2, avatar2);
        List<EmployeeView> employeeViews = List.of(employeeView1, employeeView2);

        // Configure os mocks
        when(employeeRepository.findAllCollaborators(pageable)).thenReturn(employeePage);
        when(employeeViewMapper.map(employee1)).thenReturn(employeeView1);
        when(employeeViewMapper.map(employee2)).thenReturn(employeeView2);

        // Act
        Page<EmployeeView> result = employeeService.getAllCollaborators(pageable);

        // Assert
        assertNotNull(result, "Expected page of employee views to be not null");
        assertEquals(2, result.getTotalElements(), "Expected total elements to be 2");
        assertIterableEquals(employeeViews, result.getContent(), "The content of the page does not match the expected employee views");
    }

    @Test
    @DisplayName("Should return empty page when no collaborators found")
    void shouldReturnEmptyPageWhenNoCollaboratorsFound() {
        // Arrange
        Pageable pageable = mock(Pageable.class);
        when(employeeRepository.findAllCollaborators(pageable)).thenReturn(Page.empty());

        // Act
        Page<EmployeeView> result = employeeService.getAllCollaborators(pageable);

        // Assert
        assertTrue(result.isEmpty(), () -> "Expected an empty page of employee views");
    }


}
