package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity(name = "Tb_BGifts")
public class BGift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @ManyToOne
    @JoinColumn(name = "id_gift_type", nullable = false)
    private GiftType giftType;

    @ManyToOne
    @JoinColumn(name = "id_printing_type", nullable = false)
    private PrintingType printingType;

    @ManyToOne
    @JoinColumn(name = "id_printing_shirt_type", nullable = false)
    private PrintingShirtType printingShirtType;

    @ManyToOne
    @JoinColumn(name = "id_stamp", nullable = false)
    private Stamp stamp;

    @ManyToOne
    @JoinColumn(name = "id_calendar_type", nullable = false)
    private CalendarType calendarType;

    @Column(name = "gift_model")
    private String giftModel;

    @Column(name = "link_model")
    private String linkModel;
}