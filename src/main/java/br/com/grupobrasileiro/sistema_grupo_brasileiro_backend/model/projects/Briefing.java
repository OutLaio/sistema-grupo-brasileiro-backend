package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

/**
 * Representa um briefing associado a um projeto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_Briefings")
public class Briefing {

    /**
     * O identificador único do briefing.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O projeto ao qual o briefing está associado.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    /**
     * O tipo de briefing.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_briefing_types", nullable = false)
    private BriefingType briefingType;

    /**
     * A data de início do briefing.
     */
    @Column(name = "start_time")
    private LocalDate startTime;

    /**
     * A data esperada para a conclusão do briefing.
     */
    @Column(name = "expected_time")
    private LocalDate expectedTime;

    /**
     * A data de conclusão do briefing.
     */
    @Column(name = "finish_time")
    private LocalDate finishTime;

    /**
     * Descrição detalhada do briefing.
     */
    @Column(name = "detailed_description")
    private String detailedDescription;

    /**
     * Nome da outra empresa associada ao briefing, se houver.
     */
    @Column(name = "other_company")
    private String otherCompany;

}
