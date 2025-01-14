package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSimpleViewMapper implements Mapper<Employee, EmployeeSimpleView> {

    @Override
    public EmployeeSimpleView map(Employee employee) {
        if (employee == null) {
            return null;
        }

        String fullName = employee.getName() + " " + employee.getLastName();

        return new EmployeeSimpleView(
                employee.getId(),
                fullName,
                employee.getAvatar()
        );
    }
}
