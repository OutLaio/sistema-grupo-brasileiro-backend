package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um tipo de briefing no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
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
}
