package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tb_Profiles")
public class Profile {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;

	@Column(name = "role", nullable = false)
    private Integer role;
	
	@Column(name = "description", nullable = false)
    private String description;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		return Objects.equals(id, other.id) && Objects.equals(role, other.role) 
				&& Objects.equals(description, other.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role, description);
	}
	
	
}
