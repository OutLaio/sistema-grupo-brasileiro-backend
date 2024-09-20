package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa o perfil de um usuário no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "\"Tb_Profiles\"") // Use aspas duplas para o nome da tabela
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
}
