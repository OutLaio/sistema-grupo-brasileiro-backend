package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.printed;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.BPrintedsDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form.BPrintedsFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view.BPrintedsDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.BPrintedRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintedTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;

public class PrintedService {
	
	@Autowired
    private PrintedTypeRepository printedTypeRepository;
	
	@Autowired
    private PrintingTypeRepository printingTypeRepository;
	
	@Autowired
	private BPrintedRepository bPrintedRepository;
	
	@Autowired
    private BPrintedsDetailedViewMapper bPrintedsDetailedViewMapper;
	
	public BPrintedsDetailedView register(PrintedForm form, Briefing briefing) {
		BPrinted bPrinted = new BPrintedsFormMapper().map(form);
		
		PrintedType printedType = printedTypeRepository.getReferenceById(form.idPrintedType());
		PrintingType printingType = printingTypeRepository.getReferenceById(form.idPrintingType());
		
		bPrinted.setBriefing(briefing);
		bPrinted.setPrintedType(printedType);
		bPrinted.setPrintingType(printingType);
		bPrinted.setPaperType(form.paperType());
		bPrinted.setFolds(form.folds());
		bPrinted.setPages(form.pages());
		
		bPrinted = bPrintedRepository.save(bPrinted);
		return bPrintedsDetailedViewMapper.map(bPrinted);
	}

}
