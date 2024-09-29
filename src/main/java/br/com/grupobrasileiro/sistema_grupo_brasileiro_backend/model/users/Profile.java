package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Representa o perfil de um usuário no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Profiles\"")
public class Profile {

	/**
	 * O identificador único do perfil.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;


	/**
	 * A descrição do perfil.
	 * Este campo não pode ser nulo.
	 */
	@Column(name = "description", nullable = false)
	private String description;

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Profile profile = (Profile) o;
		return Objects.equals(id, profile.id) &&
				Objects.equals(description, profile.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, description);
	}

	@Override
	public String toString() {
		return "Profile{" +
				"id=" + id +
				", description='" + description + '\'' +
				'}';
	}
}
