package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_OtherRoutes\"")
public class OtherRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_b_agency_board", nullable = false)
    private BAgencyBoard bAgencyBoard;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "type", nullable = false)
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtherRoute that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(bAgencyBoard, that.bAgencyBoard) &&
                Objects.equals(company, that.company) &&
                Objects.equals(city, that.city) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bAgencyBoard, company, city, type);
    }

    @Override
    public String toString() {
        return "OtherRoute{" +
                "id=" + id +
                ", bAgencyBoard=" + (bAgencyBoard != null ? bAgencyBoard.getId() : "null") +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
