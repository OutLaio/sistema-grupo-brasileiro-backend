package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Representa um tipo de briefing no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BriefingTypes\"")
public class BriefingType {

    /**
     * O identificador único do tipo de briefing.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * A descrição do tipo de briefing.
     * Este campo não pode ser nulo.
     */
    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BriefingType that = (BriefingType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "BriefingType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
