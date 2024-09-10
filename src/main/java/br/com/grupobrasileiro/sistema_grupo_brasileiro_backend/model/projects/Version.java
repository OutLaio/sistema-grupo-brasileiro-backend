package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.*;

/**
 * Representa uma versão de um briefing no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_Versions")
public class Version {

    /**
     * O identificador único da versão.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O briefing associado a esta versão.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    /**
     * O número da versão.
     * Este campo não pode ser nulo.
     */
    @Column(name = "num_version", nullable = false)
    private Integer numVersion;

    /**
     * Link para o produto relacionado a esta versão.
     */
    @Column(name = "product_link")
    private String productLink;

    /**
     * Indica se a versão foi aprovada pelo cliente.
     */
    @Column(name = "client_approve")
    private Boolean clientApprove;

    /**
     * Indica se a versão foi aprovada pelo supervisor.
     */
    @Column(name = "supervisor_approve")
    private Boolean supervisorApprove;

    /**
     * Comentários ou feedback sobre a versão.
     */
    @Column(name = "feedback")
    private String feedback;
}
