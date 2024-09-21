package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por mapear a entidade Employee para a view EmployeeView.
 * Implementa a interface {@link Mapper}, que define a operação de mapeamento de
 * entidades para objetos de visualização.
 */
@Component
public class EmployeeViewMapper implements Mapper<Employee, EmployeeView> {

    @Autowired
    private UserViewMapper userViewMapper;

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
        return new EmployeeView(
            employee.getId(),
            userViewMapper.map(employee.getUser()),
            employee.getName(),
            employee.getLastName(),
            employee.getPhoneNumber(),
            employee.getSector(),
            employee.getOccupation(),
            employee.getAgency(),
            employee.getAvatar()
        );
    }
}