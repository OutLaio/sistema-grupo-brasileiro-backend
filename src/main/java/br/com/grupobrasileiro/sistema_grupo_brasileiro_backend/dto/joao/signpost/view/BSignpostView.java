package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.view;

/**
 * Objeto de Transferência de Dados (DTO) para visualização de Placa (Signpost).
 * Este DTO encapsula as informações de uma placa para exibição.
 *
 * @param id             O ID da placa.
 * @param materialView   As informações do material da placa, representadas pelo DTO {@link MaterialView}.
 * @param boardLocation A localização da placa.
 * @param setor          O setor onde a placa está localizada.
 */
public record BSignpostView(

        Long id,

        MaterialView materialView,

        String boardLocation,

        String setor

) {
}
