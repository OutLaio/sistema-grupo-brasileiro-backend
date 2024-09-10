package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.register;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Placa (Signpost).
 * Este DTO encapsula as informações necessárias para o registro de uma placa.
 *
 * @param idMaterial     O ID do material da placa.
 * @param boardLocation A localização da placa.
 * @param setor          O setor onde a placa está localizada.
 */
public record BSignpostForm(


        Long idMaterial,

        String boardLocation,

        String setor

) {
}
