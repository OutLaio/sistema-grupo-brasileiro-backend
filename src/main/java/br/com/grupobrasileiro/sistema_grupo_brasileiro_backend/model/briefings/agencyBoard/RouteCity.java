package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_RoutesCities\"")
public class RouteCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_city", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "id_route", nullable = false)
    private Route route;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteCity routeCity)) return false;
        if (!id.equals(routeCity.id)) return false;
        if (!city.equals(routeCity.city)) return false;
        return route.equals(routeCity.route);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + route.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RouteCity{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", route=" + route +
                '}';
    }
}
