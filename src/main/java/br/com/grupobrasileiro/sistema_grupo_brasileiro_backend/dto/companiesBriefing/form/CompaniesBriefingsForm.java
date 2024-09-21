package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto de Transferência de Dados (DTO) para o cadastro de Briefings de Empresas.
 * Este DTO encapsula as informações necessárias para associar um briefing a uma empresa.
 *
 * @param idCompany  O ID da empresa. Não pode ser nulo.
 */
public record CompaniesBriefingsForm(

        @NotNull(message = "The company of the briefing cannot be null")
        @JsonAlias({"id_company"})
        Long idCompany
) {
}
