package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

@Component
public class BPrintedsViewMapper implements Mapper<BPrinted, PrintedView> {
	
	@Autowired
	PrintingTypeViewMapper printingTypeViewMapper;
	
	@Autowired
	PrintedTypeViewMapper printedTypeViewMapper;

	@Override
	public PrintedView map(BPrinted i) {
		PrintedType printedType = i.getPrintedType();
		PrintingType printingType = i.getPrintingType();
		
		PrintedTypeView printedTypeView = printedTypeViewMapper.map(printedType);
		PrintingTypeView printingTypeView = printingTypeViewMapper.map(printingType);
		
		return new PrintedView(
				i.getId(),
				printedTypeView,
				printingTypeView,
				i.getPaperType(),
				i.getFolds(),
				i.getPages()
		);
	}

}
