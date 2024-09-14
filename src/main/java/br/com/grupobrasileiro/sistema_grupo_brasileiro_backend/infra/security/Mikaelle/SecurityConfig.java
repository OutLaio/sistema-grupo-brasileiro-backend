package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.Mikaelle;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Classe de configuração de segurança responsável por fornecer o mecanismo de codificação de senhas.
 *
 * <p>Esta classe configura um bean do tipo {@link PasswordEncoder} usando o algoritmo
 * {@link BCryptPasswordEncoder}, que é amplamente utilizado para codificação de senhas em aplicações
 * de segurança, devido à sua robustez e resistência a ataques de força bruta.</p>
 *
 * <p>O bean {@link PasswordEncoder} estará disponível no contexto da aplicação e pode ser
 * injetado em qualquer parte do código onde a codificação de senhas for necessária.</p>
 *
 * <p>Este método faz parte da configuração de segurança geral da aplicação, fornecendo um
 * mecanismo seguro e eficiente para a codificação de senhas de usuários, antes de serem
 * persistidas no banco de dados.</p>
 */
@Configuration
public class SecurityConfig {

    /**
     * Cria e retorna um bean {@link PasswordEncoder} que utiliza o algoritmo
     * {@link BCryptPasswordEncoder} para codificar as senhas dos usuários.
     *
     * <p>O algoritmo BCrypt é uma das abordagens mais seguras para proteger senhas,
     * pois adiciona um fator de custo computacional e um salt aleatório para cada senha
     * codificada, tornando mais difícil a sua quebra por ataques de força bruta.</p>
     *
     * @return uma instância de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}