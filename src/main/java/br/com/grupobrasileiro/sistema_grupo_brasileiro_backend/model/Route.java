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
@Entity(name = "Tb_Routes")
public class Route {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bAgency_board")
    private BAgencyBoard bAgencyBoard;

    @ManyToOne
    @JoinColumn(name = "id_city_company")
    private CompanyCity companyCity;

    private String type;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Route obj = (Route) o;
		return Objects.equals(id, obj.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
    
	@Override
    public String toString() {
      return "Route{" + "id=" + id + '}';
    }
    
}
