package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.profile.register;

import java.math.BigInteger;

/**
 * Formulário de perfil que contém informações sobre o perfil do usuário.
 *
 * <p>Esta classe de dados encapsula as informações necessárias para o perfil,
 * incluindo o código da função ({@code role}) e uma descrição associada ({@code description}).</p>
 */
public record ProfileForm(

        /**
         * Código da função do perfil.
         *
         * <p>Este campo representa a função ou o papel do usuário no sistema como um número inteiro.</p>
         *
         * @return O código da função do perfil.
         */
        BigInteger role,

        /**
         * Descrição do perfil.
         *
         * <p>Este campo fornece uma descrição textual do perfil, fornecendo mais detalhes sobre a função ou o papel do usuário.</p>
         *
         * @return A descrição do perfil.
         */
        String description
) {
}
