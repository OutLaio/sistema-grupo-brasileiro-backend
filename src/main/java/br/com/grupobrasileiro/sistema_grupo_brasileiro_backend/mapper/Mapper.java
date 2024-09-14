package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.springframework.stereotype.Component;

@Component
public interface Mapper<Input, Output> {
	public Output map(Input i);

}
