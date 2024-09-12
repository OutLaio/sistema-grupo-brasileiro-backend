package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.Mikaelle;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

/**
 * Classe responsável por mapear a entidade Employee para a view EmployeeView.
 * Implementa a interface {@link Mapper}, que define a operação de mapeamento de
 * entidades para objetos de visualização.
 */
@Component
public class EmployeeViewMapper implements Mapper<Employee, EmployeeView> {
    
    /**
     * Mapeia um objeto {@link Employee} para um objeto {@link EmployeeView}.
     *
     * @param employee a entidade {@link Employee} que será mapeada para a view
     *                 {@link EmployeeView}.
     * @return um objeto {@link EmployeeView} contendo os dados mapeados da entidade
     *         {@link Employee}.
     */
    @Override
    public EmployeeView map(Employee employee) {
        // Mapeia os dados da entidade Employee para a EmployeeView
        return new EmployeeView(
            employee.getId(),                   // ID do funcionário
            employee.getUser().getId(),          // ID do usuário relacionado ao funcionário
            employee.getName(),                  // Nome do funcionário
            employee.getLastName(),              // Sobrenome do funcionário
            employee.getPhoneNumber(),           // Número de telefone do funcionário
            employee.getSector(),                // Setor de trabalho do funcionário
            employee.getOccupation(),            // Ocupação do funcionário
            null                                 // Campo adicional (avatar ou outro dado, se necessário)
        );
    }
}