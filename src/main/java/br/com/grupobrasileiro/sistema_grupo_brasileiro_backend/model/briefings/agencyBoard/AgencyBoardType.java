package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "Tb_AgencyBoardTypes")
public class AgencyBoardType {

    /**
     * O identificador único do tipo de placa de agencia.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A descrição do tipo de placa de agencia.
     * Este campo não pode ser nulo.
     */
    @Column(name = "description", nullable = false)
    private String description;


}
