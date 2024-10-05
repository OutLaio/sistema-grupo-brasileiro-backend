package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintedTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;

@Component
public class BPrintedsFormMapper implements Mapper<PrintedForm, BPrinted> {
	
	@Autowired
    private PrintedTypeRepository printedTypeRepository;
	
	@Autowired
    private PrintingTypeRepository printingTypeRepository;

	@Override
	public BPrinted map(PrintedForm i) {
		if (i == null) {
            return null;
        }
		
		PrintedType printedType = printedTypeRepository.getReferenceById(i.idPrintedType());
		PrintingType printingType = printingTypeRepository.getReferenceById(i.idPrintingType());
        
        return new BPrinted(
                null,
                null,
                printedType,
                printingType,
                i.paperType(),
                i.folds(),
                i.pages()
        );
	}
}

