package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.view;

import java.math.BigInteger;

/**
 * Representa uma visão das informações de perfil para exibição.
 *
 * <p>Este record contém os detalhes do perfil, incluindo o identificador, o código da função (role)
 * e a descrição do perfil. É utilizado para exibir informações do perfil sem incluir detalhes sensíveis.</p>
 */
public record ProfileView(

        /**
         * Identificador único do perfil.
         * Este campo armazena o ID do perfil.
         */
        Long id,

        /**
         * Código da função (role) associado ao perfil.
         * Utiliza o tipo {@link BigInteger} para acomodar valores grandes, se necessário.
         */
        BigInteger role,

        /**
         * Descrição do perfil.
         * Este campo fornece uma descrição legível do perfil.
         */
        String description

) {
}
