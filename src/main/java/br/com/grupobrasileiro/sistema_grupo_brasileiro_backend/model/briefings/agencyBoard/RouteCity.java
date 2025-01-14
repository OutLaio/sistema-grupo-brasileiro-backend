package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
        if (!(o instanceof RouteCity that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(city, that.city) &&
                Objects.equals(route, that.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, route);
    }

    @Override
    public String toString() {
        return "RouteCity{" +
                "id=" + id +
                ", city=" + (city != null ? city.getId() : "null") +
                ", route=" + (route != null ? route.getId() : "null") +
                '}';
    }

}
