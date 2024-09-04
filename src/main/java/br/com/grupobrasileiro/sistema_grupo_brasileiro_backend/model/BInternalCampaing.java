package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

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
@Entity(name = "Tb_BinternalCampaing")
public class BInternalCampaing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_stationery_type")
    private StationeryTypes stationeryType;


    @ManyToOne
    @JoinColumn(name = "id_other_items")
    private OtherItems otherItems;

    private String orgaos;
}
