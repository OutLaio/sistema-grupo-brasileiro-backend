package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa um empregado do sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "\"Tb_Employees\"")
public class Employee {

    /**
     * O identificador único do empregado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O nome do empregado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String name;

    /**
     * O sobrenome do empregado.
     * Este campo não pode ser nulo.
     */
    @Column(name = "lastname", nullable = false)
    private String lastName;

    /**
     * O número de telefone do empregado.
     * Este campo não pode ser nulo.
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * O setor em que o empregado trabalha.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String sector;

    /**
     * A ocupação do empregado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String occupation;

    /**
     * A agência onde o empregado trabalha.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String agency;

    /**
     * A indicação do avatar do empregado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private Long avatar;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Project> ownedProjects = new HashSet<>();

    @OneToMany(mappedBy = "collaborator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Project> assignedProjects = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DialogBox> dialogs = new HashSet<>();
}