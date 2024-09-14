package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.Mikaelle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.Mikaelle.EmployeeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
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

    /**
     * Adiciona um novo empregado ao sistema.
     * 
     * <p>Este método recebe os dados do empregado em um formulário {@link EmployeeForm} e
     * cria um novo {@link Employee} associado a um {@link User} existente. Os dados do
     * empregado, como nome, setor e ocupação, são preenchidos com base no formulário fornecido.</p>
     *
     * @param form o formulário contendo os dados do novo empregado
     * @param user o usuário associado ao empregado
     * @return o empregado recém-criado e salvo no banco de dados
     * @throws EntityNotFoundException se o usuário não for encontrado
     */
    @Transactional
    public Employee addEmployee(EmployeeForm form, User user) {
        Employee employee = new Employee();
        employee.setName(form.name());
        employee.setLastName(form.lastname());
        employee.setPhoneNumber(form.phoneNumber());
        employee.setSector(form.sector());
        employee.setOccupation(form.occupation());
        employee.setAgency(form.agency());
        employee.setAvatar(form.avatar());
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    /**
     * Atualiza os dados de um empregado existente.
     *
     * <p>Este método permite a atualização dos dados de um {@link Employee} com base no ID fornecido.
     * Caso o empregado não seja encontrado, uma {@link EntityNotFoundException} será lançada.
     * As informações são atualizadas com os dados fornecidos no {@link EmployeeForm}.</p>
     *
     * @param id o ID do empregado a ser atualizado
     * @param form o formulário contendo os dados atualizados do empregado
     * @return uma representação visual do empregado atualizado {@link EmployeeView}
     * @throws EntityNotFoundException se o empregado com o ID fornecido não for encontrado
     */
    @Transactional
    public EmployeeView updateEmployee(Long id, EmployeeForm form) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        employee.setName(form.name());
        employee.setLastName(form.lastname());
        employee.setPhoneNumber(form.phoneNumber());
        employee.setSector(form.sector());
        employee.setOccupation(form.occupation());
        employee.setAgency(form.agency());
        employee.setAvatar(form.avatar());
        employeeRepository.save(employee);
        return employeeViewMapper.map(employee);
    }

    /**
     * Busca um empregado pelo seu ID.
     *
     * <p>Este método busca um {@link Employee} pelo ID fornecido. Se o empregado não for
     * encontrado, uma {@link EntityNotFoundException} será lançada.</p>
     *
     * @param id o ID do empregado
     * @return uma representação visual do empregado {@link EmployeeView}
     * @throws EntityNotFoundException se o empregado com o ID fornecido não for encontrado
     */
    @Transactional(readOnly = true)
    public EmployeeView getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com id:: " + id));
        return employeeViewMapper.map(employee);
    }

    /**
     * Lista todos os empregados com suporte à paginação.
     *
     * <p>Este método retorna uma página de empregados no formato {@link EmployeeView}.
     * A paginação é controlada pelo objeto {@link Pageable}, que define o número de itens por página e o número da página atual.</p>
     *
     * @param pageable o objeto que define as propriedades da página (tamanho e número)
     * @return uma página de {@link EmployeeView} representando os empregados
     */
    @Transactional(readOnly = true)
    public Page<EmployeeView> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
            .map(employeeViewMapper::map);
    }
}