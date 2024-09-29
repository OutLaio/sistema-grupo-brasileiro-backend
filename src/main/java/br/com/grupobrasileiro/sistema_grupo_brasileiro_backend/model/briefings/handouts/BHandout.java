package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BHandouts\"")
public class BHandout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_handout_type", nullable = false)
    private HandoutType handoutType;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BHandout that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(handoutType, that.handoutType) &&
                Objects.equals(briefing, that.briefing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, handoutType, briefing);
    }

    @Override
    public String toString() {
        return "BHandout{" +
                "id=" + id +
                ", handoutType=" + handoutType +
                ", briefing=" + briefing +
                '}';
    }

}
