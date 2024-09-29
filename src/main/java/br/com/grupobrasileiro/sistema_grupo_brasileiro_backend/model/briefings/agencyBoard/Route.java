package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Routes\"")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_b_agency_board", nullable = false)
    private BAgencyBoard bAgencyBoard;

    @ManyToOne
    @JoinColumn(name = "id_company_city", nullable = false)
    private CompanyCity companyCity;

    @Column(name = "type", nullable = false)
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        // Comparação baseada em 'bAgencyBoard', 'companyCity', e 'type'
        if (!bAgencyBoard.equals(route.bAgencyBoard)) return false;
        if (!companyCity.equals(route.companyCity)) return false;
        return type.equals(route.type);
    }

    @Override
    public int hashCode() {
        int result = bAgencyBoard.hashCode();
        result = 31 * result + companyCity.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", bAgencyBoard=" + (bAgencyBoard != null ? bAgencyBoard.getId() : "null") +
                ", companyCity=" + (companyCity != null ? companyCity.getId() : "null") +
                ", type='" + type + '\'' +
                '}';
    }
}
