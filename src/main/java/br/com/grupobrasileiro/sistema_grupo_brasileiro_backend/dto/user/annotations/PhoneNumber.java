package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação personalizada para validar números de telefone.
 *
 * <p>Esta anotação combina várias restrições comuns para números de telefone:
 * <ul>
 *     <li>Não pode estar em branco ({@link NotBlank})</li>
 *     <li>Deve seguir um padrão específico de número de telefone ({@link Pattern})</li>
 *     <li>O comprimento máximo é de 20 caracteres ({@link Size})</li>
 * </ul>
 * </p>
 *
 * <p>Ela pode ser aplicada a campos e parâmetros para garantir que o
 * número de telefone fornecido esteja no formato correto e atenda a todas as restrições definidas.</p>
 */
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "{phonenumber.required}")
@Pattern(regexp = "^(?:(?:\\+|00)?55\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?((?:9\\d|[2-9])\\d{3})-?(\\d{4})$", message = "{phonenumber.invalid}")
@Size(max = 20, message = "{phonenumber.size}")
public @interface PhoneNumber {

    /**
     * Mensagem padrão para a violação da restrição.
     *
     * @return A mensagem de erro padrão.
     */
    String message() default "Invalid phone number format!";

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
