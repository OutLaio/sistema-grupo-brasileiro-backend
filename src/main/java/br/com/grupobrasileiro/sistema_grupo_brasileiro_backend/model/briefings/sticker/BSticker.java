package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerInformationType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BStickers\"")
public class BSticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @ManyToOne
    @JoinColumn(name = "id_sticker_type", nullable = false)
    private StickerType stickerType;

    @ManyToOne
    @JoinColumn(name = "id_sticker_information_type")
    private StickerInformationType stickerInformationType;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "observations", nullable = false)
    private String observations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BSticker that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(briefing, that.briefing) &&
                Objects.equals(stickerType, that.stickerType) &&
                Objects.equals(stickerInformationType, that.stickerInformationType) &&
                Objects.equals(sector, that.sector) &&
                Objects.equals(observations, that.observations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, briefing, stickerType, stickerInformationType, sector, observations);
    }

    @Override
    public String toString() {
        return "BSticker{" +
                "id=" + id +
                ", briefing=" + briefing +
                ", stickerType=" + stickerType +
                ", stickerInformationType=" + stickerInformationType +
                ", sector='" + sector + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
