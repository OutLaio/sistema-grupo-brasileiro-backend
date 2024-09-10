package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Representa um token desabilitado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_RecoveryTokens")
public class RecoveryToken {

    /**
     * O identificador único do token de recuperação desabilitado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O valor do token de recuperação desabilitado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String token;

}
