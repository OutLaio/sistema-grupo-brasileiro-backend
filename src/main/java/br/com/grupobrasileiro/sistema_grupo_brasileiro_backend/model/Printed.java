package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tb_BPrinteds")
public class Printed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_version")
    private Version version;

    @ManyToOne
    @JoinColumn(name = "id_paper_type")
    private PaperType paperType;

    @ManyToOne
    @JoinColumn(name = "id_printed_type")
    private PrintedType printedType;

    @ManyToOne
    @JoinColumn(name = "id_printing_type")
    private PrintingType printingType;

    private Integer folds;
    private Integer pages;

    
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Printed obj = (Printed) o;
      return Objects.equals(id, obj.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    @Override
    public String toString() {
      return "Printed{" + "id=" + id + '}';
    }
}
