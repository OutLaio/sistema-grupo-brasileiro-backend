package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.meansurements;

import java.math.BigDecimal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa uma medição associada a um briefing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "\"Tb_measurements\"")
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

}
