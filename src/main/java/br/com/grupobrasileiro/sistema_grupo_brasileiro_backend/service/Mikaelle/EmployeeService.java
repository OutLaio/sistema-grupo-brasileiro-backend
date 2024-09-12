package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.Mikaelle.EmployeeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.Mikaelle.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe de serviço responsável por operações relacionadas à entidade Employee.
 */

@Service
public class EmployeeService {

    @Autowired
	private EmployeeRepository employeeRepository;

    @Autowired
	private EmployeeViewMapper employeeViewMapper;

    @Autowired
	private UserRepository userRepository;

    /**
     * Método para adicionar um novo Employee ao sistema.
     * Recebe os dados do Employee via formulário (EmployeeForm) e o ID do usuário associado.
     * Verifica se o usuário existe e cria um novo Employee com base nos dados fornecidos.
     *
     * @param form objeto EmployeeForm contendo os dados do novo Employee
     * @param userId ID do usuário (User) associado ao Employee
     * @return o Employee recém-criado e salvo no banco de dados
     * @throws IllegalArgumentException se o usuário com o ID fornecido não for encontrado
     */
    @Transactional
    public Employee addEmployee(EmployeeForm form, Long userId) {
        // Verifica se o usuário com o id fornecido existe no banco de dados
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Cria uma nova instância de Employee
        Employee employee = new Employee();
        employee.setName(form.name());               // Define o nome do Employee
        employee.setLastName(form.lastname());       // Define o sobrenome
        employee.setPhoneNumber(form.phoneNumber()); // Define o número de telefone
        employee.setSector(form.sector());           // Define o setor de trabalho
        employee.setOccupation(form.occupation());   // Define a ocupação
        employee.setAgency(form.agency());           // Define a agência
        employee.setAvatar(form.avatar());           // Define o avatar do Employee
        employee.setUser(user);                      // Associa o usuário (User) ao Employee

        // Salva o novo Employee no repositório e retorna o objeto salvo
        return employeeRepository.save(employee);
    }


    @Transactional
    public EmployeeView updateEmployee(Long Id, EmployeeForm form) {
        // Busca o funcionário pelo ID. Se não for encontrado, lança uma exceção com mensagem apropriada.
        Employee employee = employeeRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Atualiza os campos da entidade Employee com os valores fornecidos pelo EmployeeForm
        employee.setName(form.name());                // Atualiza o nome do funcionário
        employee.setLastName(form.lastname());        // Atualiza o sobrenome
        employee.setPhoneNumber(form.phoneNumber());  // Atualiza o número de telefone
        employee.setSector(form.sector());            // Atualiza o setor de trabalho
        employee.setOccupation(form.occupation());    // Atualiza a ocupação do funcionário
        employee.setAgency(form.agency());            // Atualiza a agência do funcionário
        employee.setAvatar(form.avatar());            // Atualiza o avatar do funcionário

        // Salva a entidade Employee atualizada no repositório (banco de dados)
        employeeRepository.save(employee);

        // Mapeia a entidade Employee para EmployeeView e retorna a visão mapeada
        return employeeViewMapper.map(employee);
    }

    /**
     * Serviço para buscar um Employee pelo ID.
     * @param id o ID do Employee.
     * @return EmployeeView representando o Employee.
     */
    @Transactional(readOnly = true)
    public EmployeeView getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
        return employeeViewMapper.map(employee);
    }

    /**
     * Serviço para listar todos os Employees com paginação.
     * @param pageable objeto que define as propriedades da página (número e tamanho).
     * @return Página de EmployeeView representando os Employees.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeView> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
            .map(employeeViewMapper::map);
    }
}
