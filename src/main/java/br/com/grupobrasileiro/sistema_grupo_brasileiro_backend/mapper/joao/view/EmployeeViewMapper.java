package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeViewMapper implements Mapper<Employee, EmployeeView> {

    @Autowired
    private UserViewMapper userMapperView;

    @Override
    public EmployeeView map(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeView(
                employee.getId(),
                userMapperView.map(employee.getUser()),
                employee.getName(),
                employee.getLastName(),
                employee.getPhoneNumber(),
                employee.getSector(),
                employee.getOccupation(),
                employee.getAvatar()
        );
    }
}
