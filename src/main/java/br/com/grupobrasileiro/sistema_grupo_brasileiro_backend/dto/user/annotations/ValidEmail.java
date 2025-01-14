package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação personalizada para validar endereços de e-mail.
 *
 * <p>Esta anotação combina várias restrições comuns para e-mails:
 * <ul>
 *     <li>Não pode estar em branco ({@link NotBlank})</li>
 *     <li>Deve estar em um formato de e-mail válido ({@link Email})</li>
 *     <li>O comprimento máximo é de 50 caracteres ({@link Size})</li>
 * </ul>
 * </p>
 *
 * <p>Ela pode ser aplicada a campos e parâmetros para garantir que
 * o e-mail fornecido atenda a todas as restrições definidas.</p>
 */
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "{email.required}")
@Email(message = "{email.invalid}")
@Size(max = 50, message = "{email.size}")
public @interface ValidEmail {

    /**
     * Mensagem padrão para a violação da restrição.
     *
     * @return A mensagem de erro padrão.
     */
    String message() default "{email.invalid}";

    /**
     * Grupos de validação que esta anotação pertence.
     *
     * @return Os grupos de validação.
     */
    Class<?>[] groups() default {};

    /**
     * Define informações de carga útil para clientes que consomem a anotação.
     *
     * @return As classes de carga útil.
     */
    Class<? extends Payload>[] payload() default {};
}
