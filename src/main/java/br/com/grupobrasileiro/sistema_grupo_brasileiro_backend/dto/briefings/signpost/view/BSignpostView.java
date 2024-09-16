package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Placa (Signpost).
 * Este DTO encapsula as informações de uma placa para exibição.
 *
 * @param id             O ID da placa.
 * @param material       As informações do material da placa, representadas pelo DTO {@link MaterialView}.
 * @param boardLocation  A localização da placa.
 * @param sector         O setor onde a placa está localizada.
 */
public record BSignpostView(

        Long id,
        MaterialView material,
        String boardLocation,
        String sector

) {
}
