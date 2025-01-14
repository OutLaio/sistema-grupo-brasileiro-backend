package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um briefing associado a um projeto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Briefings\"")
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
    @OneToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    /**
     * O tipo de briefing.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_briefing_type", nullable = false)
    private BriefingType briefingType;

    /**
     * A data de início do briefing.
     * Este campo não pode ser nulo.
     */
    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    /**
     * A data esperada para a conclusão do briefing.
     * Este campo não pode ser nulo.
     */
    @Column(name = "expected_time", nullable = false)
    private LocalDate expectedTime;

    /**
     * A data de conclusão do briefing.
     */
    @Column(name = "finish_time")
    private LocalDate finishTime;

    /**
     * Descrição detalhada do briefing.
     * Este campo não pode ser nulo.
     */
    @Column(name = "detailed_description", nullable = false)
    private String detailedDescription;

    /**
     * Nome da outra empresa associada ao briefing, se houver.
     */
    @Column(name = "other_company")
    private String otherCompany;

    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Version> versions = new HashSet<>();

    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<DialogBox> dialogs = new HashSet<>();

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private Measurement measurement;

    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<CompaniesBriefing> companies = new HashSet<>();

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BPrinted printed;

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BGift gift;

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BAgencyBoard agencyBoard;

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BSticker sticker;

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BSignpost signpost;

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BHandout handout;

    @OneToOne(mappedBy = "briefing")
    @JsonIgnore
    private BInternalCampaign internalCampaign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Briefing briefing)) return false;
        return Objects.equals(id, briefing.id) &&
                Objects.equals(project, briefing.project) &&
                Objects.equals(briefingType, briefing.briefingType) &&
                Objects.equals(startTime, briefing.startTime) &&
                Objects.equals(expectedTime, briefing.expectedTime) &&
                Objects.equals(finishTime, briefing.finishTime) &&
                Objects.equals(detailedDescription, briefing.detailedDescription) &&
                Objects.equals(otherCompany, briefing.otherCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project, briefingType, startTime, expectedTime, finishTime, detailedDescription, otherCompany);
    }

    @Override
    public String toString() {
        return "Briefing{" +
                "id=" + id +
                ", project=" + (project != null ? project.getId() : "null") +
                ", briefingType=" + (briefingType != null ? briefingType.getId() : "null") +
                ", startTime=" + startTime +
                ", expectedTime=" + expectedTime +
                ", finishTime=" + finishTime +
                ", detailedDescription='" + detailedDescription + '\'' +
                ", otherCompany='" + otherCompany + '\'' +
                '}';
    }
}
