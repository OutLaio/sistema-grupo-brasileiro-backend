package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.time.LocalDateTime;
import java.util.Objects;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa uma caixa de diálogo no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_DialogBoxes\"")
public class DialogBox {

    /**
     * O identificador único da caixa de diálogo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O empregado associado à caixa de diálogo.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_employee", nullable = false)
    private Employee employee;

    /**
     * O briefing associado à caixa de diálogo.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    /**
     * O momento em que a caixa de diálogo foi criada.
     * Este campo não pode ser nulo.
     */
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    /**
     * O conteúdo da caixa de diálogo.
     * Pode conter um texto longo.
     * Este campo não pode ser nulo.
     */
    @Lob
    @Column(name = "dialog", nullable = false)
    private String dialog;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DialogBox dialogBox = (DialogBox) o;
        return Objects.equals(id, dialogBox.id) &&
                Objects.equals(employee != null ? employee.getId() : null, dialogBox.employee != null ? dialogBox.employee.getId() : null) &&
                Objects.equals(briefing != null ? briefing.getId() : null, dialogBox.briefing != null ? dialogBox.briefing.getId() : null) &&
                Objects.equals(time, dialogBox.time) &&
                Objects.equals(dialog, dialogBox.dialog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                employee != null ? employee.getId() : null,
                briefing != null ? briefing.getId() : null,
                time,
                dialog);
    }

    @Override
    public String toString() {
        return "DialogBox{" +
                "id=" + id +
                ", employee=" + (employee != null ? employee.getId() : "null") +
                ", briefing=" + (briefing != null ? briefing.getId() : "null") +
                ", time=" + time +
                ", dialog='" + dialog + '\'' +
                '}';
    }

}
