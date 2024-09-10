package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.time.LocalDateTime;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.*;

/**
 * Representa uma caixa de diálogo no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_DialogBoxes")
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
}
