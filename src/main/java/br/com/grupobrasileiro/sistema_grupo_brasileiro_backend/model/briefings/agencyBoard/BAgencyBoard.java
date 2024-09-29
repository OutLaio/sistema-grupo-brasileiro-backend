package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BAgencyBoards\"")
public class BAgencyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_agency_board_type", nullable = false)
    private AgencyBoardType agencyBoardType;

    @ManyToOne
    @JoinColumn(name = "id_board_type")
    private BoardType boardType;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @Column(name = "board_location", nullable = false)
    private String boardLocation;

    @Lob
    @Column(name = "observations", nullable = false)
    private String observations;

    @OneToMany(mappedBy = "bAgencyBoard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Route> routes = new HashSet<>();

    @OneToMany(mappedBy = "bAgencyBoard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OtherRoute> otherRoutes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BAgencyBoard that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(agencyBoardType, that.agencyBoardType) &&
                Objects.equals(boardType, that.boardType) &&
                Objects.equals(briefing, that.briefing) &&
                Objects.equals(boardLocation, that.boardLocation) &&
                Objects.equals(observations, that.observations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agencyBoardType, boardType, briefing, boardLocation, observations);
    }

    @Override
    public String toString() {
        return "BAgencyBoard{" +
                "id=" + id +
                ", agencyBoardType=" + (agencyBoardType != null ? agencyBoardType.getId() : "null") +
                ", boardType=" + (boardType != null ? boardType.getId() : "null") +
                ", briefing=" + (briefing != null ? briefing.getId() : "null") +
                ", boardLocation='" + boardLocation + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
