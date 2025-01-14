package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view;

public record PrintedView(
        Long id,
        PrintedTypeView printedType,
        PrintingTypeView printingType,
        String paperType,
        Integer folds,
        Integer pages
) {
}
