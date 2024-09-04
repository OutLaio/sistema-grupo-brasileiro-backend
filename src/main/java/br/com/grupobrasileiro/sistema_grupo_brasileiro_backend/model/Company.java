package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;


import java.util.Objects;

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
@Entity(name = "Tb_Companies")
public class Company {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String name;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Company other = (Company) o;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
    public String toString() {
      return "Company{" + "id=" + id + '}';
    }
	
}
