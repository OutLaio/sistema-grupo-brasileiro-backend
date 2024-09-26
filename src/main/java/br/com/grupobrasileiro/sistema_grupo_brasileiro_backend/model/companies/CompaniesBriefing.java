package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa a associação entre uma empresa e um briefing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "\"Tb_CompaniesBriefing\"")
public class CompaniesBriefing {

    /**
     * O identificador único da associação entre empresa e briefing.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * A empresa associada ao briefing.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_company", nullable = false)
    private Company company;

    /**
     * O briefing associado à empresa.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;
}
