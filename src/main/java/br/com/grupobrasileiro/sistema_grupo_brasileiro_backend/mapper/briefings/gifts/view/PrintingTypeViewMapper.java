package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

@Component
public class PrintingTypeViewMapper implements Mapper<PrintingType, PrintingTypeView> {

    @Override
    public PrintingTypeView map(PrintingType printingType) {
        return new PrintingTypeView(
                printingType.getId(),
                printingType.getDescription()
        );
    }
}
