package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;


import lombok.*;
import jakarta.persistence.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_PrintingShirtTypes\"")
public class PrintingShirtType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrintingShirtType that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "PrintingShirtType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
