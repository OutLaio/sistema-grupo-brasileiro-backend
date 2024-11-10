package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.printed;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form.BPrintedsFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.BPrintedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintedService {
	
	@Autowired
	private BPrintedRepository bPrintedRepository;

	@Autowired
	private BPrintedsFormMapper bPrintedsFormMapper;

	public void register(PrintedForm form, Briefing briefing) {
		BPrinted bPrinted = bPrintedsFormMapper.map(form);
		bPrinted.setBriefing(briefing);
		bPrintedRepository.save(bPrinted);
	}
}
