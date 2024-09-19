package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity(name = "Tb_BSignposts")
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




}