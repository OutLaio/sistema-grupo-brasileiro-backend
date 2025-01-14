package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Stamps\"")
public class Stamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stamp stamp)) return false;
        return Objects.equals(id, stamp.id) &&
                Objects.equals(description, stamp.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "Stamp{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
