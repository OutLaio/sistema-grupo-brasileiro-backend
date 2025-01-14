package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BPrinteds\"")
public class BPrinted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @ManyToOne
    @JoinColumn(name = "id_printed_type", nullable = false)
    private PrintedType printedType;

    @ManyToOne
    @JoinColumn(name = "id_printing_type")
    private PrintingType printingType;

    @Column(name = "paper_type")
    private String paperType;

    @Column(name = "folds")
    private Integer folds;

    @Column(name = "pages")
    private Integer pages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BPrinted that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(briefing, that.briefing) &&
                Objects.equals(printedType, that.printedType) &&
                Objects.equals(printingType, that.printingType) &&
                Objects.equals(paperType, that.paperType) &&
                Objects.equals(folds, that.folds) &&
                Objects.equals(pages, that.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, briefing, printedType, printingType, paperType, folds, pages);
    }

    @Override
    public String toString() {
        return "BPrinted{" +
                "id=" + id +
                ", briefing=" + briefing +
                ", printedType=" + printedType +
                ", printingType=" + printingType +
                ", paperType='" + paperType + '\'' +
                ", folds=" + folds +
                ", pages=" + pages +
                '}';
    }

}