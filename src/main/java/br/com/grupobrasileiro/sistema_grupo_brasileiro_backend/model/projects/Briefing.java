package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.meansurements.Measurement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private LocalDateTime startTime;

    /**
     * A data esperada para a conclusão do briefing.
     * Este campo não pode ser nulo.
     */
    @Column(name = "expected_time", nullable = false)
    private LocalDateTime expectedTime;

    /**
     * A data de conclusão do briefing.
     */
    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    /**
     * Descrição detalhada do briefing.
     * Este campo não pode ser nulo.
     */
    @Column(name = "detailed_description", nullable = false)
    private String detailedDescription;
    


    public Briefing(BriefingForm briefingForm){
        this.startTime = LocalDateTime.now();
        this.expectedTime = briefingForm.expectedTime();
        this.detailedDescription = briefingForm.detailedDescription();

    }





    /**
     * Nome da outra empresa associada ao briefing, se houver.
     */
    @Column(name = "other_company")
    private String otherCompany;

    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Version> versions = new HashSet<>();

    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DialogBox> dialogs = new HashSet<>();

    @OneToOne(mappedBy = "briefing")
    private Measurement measurement;

    @OneToMany(mappedBy = "briefing", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CompaniesBriefing> companies = new HashSet<>();

    @OneToOne(mappedBy = "briefing")
    private BPrinted printed;

    @OneToOne(mappedBy = "briefing")
    private BGift gift;

    @OneToOne(mappedBy = "briefing")
    private BAgencyBoard agencyBoard;

    @OneToOne(mappedBy = "briefing")
    private BSticker sticker;

    @OneToOne(mappedBy = "briefing")
    private BSignpost signpost;

    @OneToOne(mappedBy = "briefing")
    private BHandout handout;

    @OneToOne(mappedBy = "briefing")
    private BInternalCampaign internalCampaign;
}
