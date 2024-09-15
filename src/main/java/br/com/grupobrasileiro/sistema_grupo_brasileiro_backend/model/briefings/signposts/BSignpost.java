package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts;



import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

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