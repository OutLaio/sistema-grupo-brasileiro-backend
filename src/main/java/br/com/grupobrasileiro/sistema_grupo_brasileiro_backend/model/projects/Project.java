package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

/**
 * Representa um projeto no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_Projects")
public class Project {

    /**
     * O identificador único do projeto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O colaborador associado ao projeto.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_collaborator", nullable = false)
    private Employee collaborator;

    /**
     * O cliente associado ao projeto.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Employee client;

    /**
     * O título do projeto.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String title;

    /**
     * O status do projeto.
     * Este campo pode ser nulo.
     */
    @Column
    private String status;

    /**
     * Indica se o projeto está desativado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private Boolean disabled;

    /**
     * Os briefings associados ao projeto.
     * A relação é mapeada pelo campo "project" na entidade Briefing.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Briefing> briefings = new HashSet<>();

}
