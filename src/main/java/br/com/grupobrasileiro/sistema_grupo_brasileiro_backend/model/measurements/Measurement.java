package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa uma medição associada a um briefing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Measurements\"")
public class Measurement {

    /**
     * O identificador único da medição.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O briefing ao qual a medição está associada.
     * Este campo não pode ser nulo.
     */
    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    /**
     * Altura medida.
     */
    @Column(name = "height")
    private BigDecimal height;

    /**
     * Comprimento medido.
     */
    @Column(name = "length")
    private BigDecimal length;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement measurement)) return false;
        return Objects.equals(id, measurement.id) &&
                Objects.equals(briefing, measurement.briefing) &&
                Objects.equals(height, measurement.height) &&
                Objects.equals(length, measurement.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, briefing, height, length);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", briefing=" + (briefing != null ? briefing.getId() : "null") +
                ", height=" + height +
                ", length=" + length +
                '}';
    }

}
