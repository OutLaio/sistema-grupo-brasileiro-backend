package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

/**
 * Objeto de Transferência de Dados (DTO) detalhado para visualização de Adesivo (Sticker) em um projeto.
 * Este DTO encapsula informações completas de um adesivo, projeto e briefing associados.
 *
 * @param sticker  Detalhes do adesivo, incluindo tipo, setor e observações.
 * @param project  Informações do projeto ao qual o adesivo pertence.
 * @param briefing Informações do briefing relacionado ao adesivo no projeto.
 */
public record BStickerDetailedView(
        BStickerView sticker,
        ProjectView project,
        BriefingView briefing
) {
}
