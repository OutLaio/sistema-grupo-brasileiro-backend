package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity(name = "Tb_BPrinteds")
public class BPrinted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @ManyToOne
    @JoinColumn(name = "id_printed_types", nullable = false)
    private PrintedType printedType;

    @ManyToOne
    @JoinColumn(name = "id_printing_types")
    private PrintingType printingType;

    @Column(name = "paper_type")
    private String paperType;

    @Column(name = "folds")
    private Integer folds;

    @Column(name = "pages")
    private Integer pages;
}