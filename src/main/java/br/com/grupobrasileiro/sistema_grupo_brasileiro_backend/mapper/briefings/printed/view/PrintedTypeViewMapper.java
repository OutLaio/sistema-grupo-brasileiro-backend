package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;

@Component
public class PrintedTypeViewMapper implements  Mapper<PrintedType, PrintedTypeView> {

	@Override
	public PrintedTypeView map(PrintedType i) {
		return new PrintedTypeView(i.getId(), i.getDescription());
	}

}
