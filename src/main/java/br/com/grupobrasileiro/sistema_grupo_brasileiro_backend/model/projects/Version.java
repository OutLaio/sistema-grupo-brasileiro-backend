package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Representa uma versão de um briefing no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Version\"")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return Objects.equals(id, version.id) &&
                Objects.equals(numVersion, version.numVersion) &&
                Objects.equals(productLink, version.productLink) &&
                Objects.equals(clientApprove, version.clientApprove) &&
                Objects.equals(supervisorApprove, version.supervisorApprove) &&
                Objects.equals(feedback, version.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numVersion, productLink, clientApprove, supervisorApprove, feedback);
    }

    @Override
    public String toString() {
        return "Version{" +
                "id=" + id +
                ", briefing=" + (briefing != null ? briefing.getId() : "null") +
                ", numVersion=" + numVersion +
                ", productLink='" + productLink + '\'' +
                ", clientApprove=" + clientApprove +
                ", supervisorApprove=" + supervisorApprove +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
