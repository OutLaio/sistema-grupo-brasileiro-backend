package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.campaingInternal;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity(name = "BInternalCampaigns")
public class BInternalCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_stationery_type", nullable = false)
    private StationeryType stationeryType;

    @ManyToOne
    @JoinColumn(name = "id_other_items", nullable = false)
    private OtherItem otherItem;

    @ManyToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @Column(name = "campaign_motto")
    private String campaignMotto;
}
