package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_StickerInformationTypes\"")
public class StickerInformationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StickerInformationType that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "StickerInformationType{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

}
