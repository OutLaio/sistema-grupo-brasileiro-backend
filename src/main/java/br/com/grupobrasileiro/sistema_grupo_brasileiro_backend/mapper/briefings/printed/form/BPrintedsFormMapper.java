package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintedTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BPrintedsFormMapper implements Mapper<PrintedForm, BPrinted> {
	
	@Autowired
    private PrintedTypeRepository printedTypeRepository;
	
	@Autowired
    private PrintingTypeRepository printingTypeRepository;

	@Override
	public BPrinted map(PrintedForm i) {
		if (i == null) {
            throw new NullPointerException("PrintedForm at Mapper is null");
        }

        
        return new BPrinted(
                null,
                null,
                printedTypeRepository.findById(i.idPrintedType()).orElseThrow(
						() -> new EntityNotFoundException("Printed Type not found with id: " + i.idPrintedType())
				),
                i.idPrintingType() == null? null : printingTypeRepository.findById(i.idPrintingType()).orElseThrow(
						() -> new EntityNotFoundException("Printing Type not found with id: " + i.idPrintingType())
				),
                i.paperType(),
                i.folds(),
                i.pages()
        );
	}
}

