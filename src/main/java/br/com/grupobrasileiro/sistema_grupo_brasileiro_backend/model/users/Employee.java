package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Representa um empregado do sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_Employees")
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
    @Column(nullable = false)
    private String lastName;

    /**
     * O número de telefone do empregado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
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
}
