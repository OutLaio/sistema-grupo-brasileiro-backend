package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;





import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BSignposts\"")
public class BSignpost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_material", nullable = false)
    private Material material;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @Column(name = "board_location", nullable = false)
    private String boardLocation;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BSignpost that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(material, that.material) &&
                Objects.equals(briefing, that.briefing) &&
                Objects.equals(boardLocation, that.boardLocation) &&
                Objects.equals(sector, that.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, material, briefing, boardLocation, sector);
    }

    @Override
    public String toString() {
        return "BSignpost{" +
                "id=" + id +
                ", material=" + material +
                ", briefing=" + briefing +
                ", boardLocation='" + boardLocation + '\'' +
                ", sector='" + sector + '\'' +
                '}';
    }

}