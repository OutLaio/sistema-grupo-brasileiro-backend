package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerInformationType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
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
}
