package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Representa um token desabilitado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "\"Tb_RecoveryTokens\"")
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


    public RecoveryToken(String token) {
        this.token = token;
    }
}
