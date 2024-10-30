package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RouteCity> routeCities = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "id_company", nullable = false)
    private Company company;

    @Column(name = "type", nullable = false)
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) &&
                Objects.equals(bAgencyBoard, route.bAgencyBoard) &&
                Objects.equals(routeCities, route.routeCities) &&
                Objects.equals(company, route.company) &&
                Objects.equals(type, route.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bAgencyBoard, routeCities, company, type);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", bAgencyBoard=" + bAgencyBoard +
                ", routeCities=" + routeCities +
                ", company=" + company +
                ", type='" + type + '\'' +
                '}';
    }

}
