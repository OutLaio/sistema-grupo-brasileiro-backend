package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Representa um usuário do sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "\"Tb_Users\"") // Use aspas duplas para o nome da tabela
public class User implements UserDetails {

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

  /**
   * Retorna as permissões associadas ao perfil do usuário.
   * No caso, retorna o `description` do `Profile` como a permissão.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(profile.getDescription()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !this.disabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !this.disabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !this.disabled;
  }

  @Override
  public boolean isEnabled() {
    return !this.disabled;
  }
}
