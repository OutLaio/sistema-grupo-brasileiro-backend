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
 * Anotação personalizada para validar senhas.
 *
 * <p>Esta anotação é uma combinação de várias restrições de validação para senhas, garantindo que
 * as senhas atendam aos critérios de complexidade e segurança definidos pela aplicação:</p>
 * <ul>
 *     <li>A senha não deve estar em branco ({@link NotBlank}).</li>
 *     <li>A senha deve ter um comprimento mínimo de 8 caracteres ({@link Size}).</li>
 *     <li>A senha deve conter pelo menos uma letra minúscula ({@link Pattern}).</li>
 *     <li>A senha deve conter pelo menos uma letra maiúscula ({@link Pattern}).</li>
 *     <li>A senha deve conter pelo menos um dígito ({@link Pattern}).</li>
 *     <li>A senha deve conter pelo menos um caractere especial ({@link Pattern}).</li>
 * </ul>
 *
 * <p>Aplicada a campos e parâmetros, esta anotação assegura que a senha fornecida pelo usuário
 * esteja de acordo com as regras de segurança da aplicação.</p>
 */
@Constraint(validatedBy = {})
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NotBlank(message = "{password.required}")
@Size(min = 8, message = "{password.minlength}")
@Pattern(regexp = "^(?=.*[a-z]).*$", message = "{password.lowercase}")
@Pattern(regexp = "^(?=.*[A-Z]).*$", message = "{password.uppercase}")
@Pattern(regexp = "^(?=.*\\d).*$", message = "{password.digit}")
@Pattern(regexp = "^(?=.*[@$!%*?&]).*$", message = "{password.specialchar}")
public @interface Password {

    /**
     * Mensagem padrão para a violação da restrição de senha.
     *
     * @return A mensagem de erro padrão.
     */
    String message() default "Invalid password format!";

    /**
     * Grupos de validação aos quais esta anotação pertence.
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
