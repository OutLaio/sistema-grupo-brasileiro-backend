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
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tb_BAgencyBoards")
@EqualsAndHashCode
@ToString(of = "id") // Generates toString method including only the 'id' field
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
}
