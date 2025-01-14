package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

/**
 * Representa um token desabilitado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecoveryToken that = (RecoveryToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token);
    }

    @Override
    public String toString() {
        return "RecoveryToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                '}';
    }
}
