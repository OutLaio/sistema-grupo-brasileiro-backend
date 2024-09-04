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
@Entity(name = "Tb_Other_Routes")
public class OtherRoute {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bAgency_board")
    private BAgencyBoard bAgencyBoard;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OtherRoute other = (OtherRoute) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
    public String toString() {
      return "OtherRoute{" + "id=" + id + '}';
    }
	
}
