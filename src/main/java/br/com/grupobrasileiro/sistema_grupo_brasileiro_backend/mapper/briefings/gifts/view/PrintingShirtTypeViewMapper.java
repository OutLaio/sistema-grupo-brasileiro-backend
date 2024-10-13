package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.PrintingShirtTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;

@Component
public class PrintingShirtTypeViewMapper implements Mapper<PrintingShirtType, PrintingShirtTypeView> {

    @Override
    public PrintingShirtTypeView map(PrintingShirtType printingShirtType) {
        return new PrintingShirtTypeView(
                printingShirtType.getId(),
                printingShirtType.getDescription()
        );
    }
}