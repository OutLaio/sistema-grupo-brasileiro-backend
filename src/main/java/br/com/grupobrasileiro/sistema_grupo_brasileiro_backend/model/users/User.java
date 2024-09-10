package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um usuário do sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_Users")
public class User {

  /**
   * O identificador único do usuário.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * O perfil associado ao usuário.
   * Este campo não pode ser nulo.
   */
  @ManyToOne
  @JoinColumn(name = "id_profile", nullable = false)
  private Profile profile;

  /**
   * O e-mail do usuário.
   * Este campo não pode ser nulo e deve ser único.
   */
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * A senha do usuário.
   * Este campo não pode ser nulo.
   */
  @Column(nullable = false)
  private String password;

  /**
   * Indica se o usuário está desativado.
   * Este campo não pode ser nulo.
   */
  @Column(nullable = false)
  private Boolean disabled;

  @OneToOne(mappedBy = "user")
  private Employee employee;
}
