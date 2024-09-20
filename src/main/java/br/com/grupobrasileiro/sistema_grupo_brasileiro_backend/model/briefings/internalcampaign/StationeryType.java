package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "\"Tb_StationeryTypes\"")
public class StationeryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;
}