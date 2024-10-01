package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BInternalCampaigns\"")
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

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @Column(name = "campaign_motto", nullable = false)
    private String campaignMotto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BInternalCampaign that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(stationeryType, that.stationeryType) &&
                Objects.equals(otherItem, that.otherItem) &&
                Objects.equals(briefing, that.briefing) &&
                Objects.equals(campaignMotto, that.campaignMotto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stationeryType, otherItem, briefing, campaignMotto);
    }

    @Override
    public String toString() {
        return "BInternalCampaign{" +
                "id=" + id +
                ", stationeryType=" + stationeryType +
                ", otherItem=" + otherItem +
                ", briefing=" + briefing +
                ", campaignMotto='" + campaignMotto + '\'' +
                '}';
    }

}
