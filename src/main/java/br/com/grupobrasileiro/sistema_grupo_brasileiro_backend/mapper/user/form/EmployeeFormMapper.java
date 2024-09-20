package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.springframework.stereotype.Component;


@Component
public class EmployeeFormMapper implements Mapper<EmployeeForm, Employee> {
    @Override
    public Employee map(EmployeeForm i) {
        return new Employee(
            null,
            i.name(),
            i.lastname(),
            i.phoneNumber(),
            i.sector(),
            i.occupation(),
            i.agency(),
            i.avatar(),
            null,
            null,
            null,
            null
        );
    }
}
