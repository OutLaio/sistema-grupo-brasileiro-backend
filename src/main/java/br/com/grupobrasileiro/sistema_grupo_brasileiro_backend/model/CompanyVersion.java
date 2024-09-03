package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tb_Company_Versions")
public class CompanyVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_version")
    private Version version;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company company;
    
    @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		CompanyVersion obj = (CompanyVersion) o;
		return Objects.equals(id, obj.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
    
	@Override
    public String toString() {
      return "CompanyVersion{" + "id=" + id + '}';
    }

}
