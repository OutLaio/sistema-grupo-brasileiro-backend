package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id") // Generates equals and hashCode methods based on the 'id' field
@ToString(of = "id") // Generates toString method including only the 'id' field
@Entity(name = "Tb_OtherRoutes")
public class OtherRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_b_agency_board", nullable = false)
    private BAgencyBoard bAgencyBoard;

    @Column(name = "company")
    private String company;

    @Column(name = "city")
    private String city;

    @Column(name = "type")
    private String type;
}
