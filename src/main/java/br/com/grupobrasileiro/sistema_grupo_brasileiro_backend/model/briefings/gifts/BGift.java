package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import lombok.*;
import jakarta.persistence.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_BGifts\"")
public class BGift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_briefing", nullable = false)
    private Briefing briefing;

    @ManyToOne
    @JoinColumn(name = "id_gift_type", nullable = false)
    private GiftType giftType;

    @ManyToOne
    @JoinColumn(name = "id_printing_type")
    private PrintingType printingType;

    @ManyToOne
    @JoinColumn(name = "id_printing_shirt_type")
    private PrintingShirtType printingShirtType;

    @ManyToOne
    @JoinColumn(name = "id_stamp")
    private Stamp stamp;

    @ManyToOne
    @JoinColumn(name = "id_calendar_type")
    private CalendarType calendarType;

    @Column(name = "gift_model")
    private String giftModel;

    @Column(name = "link_model")
    private String linkModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BGift that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(briefing, that.briefing) &&
                Objects.equals(giftType, that.giftType) &&
                Objects.equals(printingType, that.printingType) &&
                Objects.equals(printingShirtType, that.printingShirtType) &&
                Objects.equals(stamp, that.stamp) &&
                Objects.equals(calendarType, that.calendarType) &&
                Objects.equals(giftModel, that.giftModel) &&
                Objects.equals(linkModel, that.linkModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, briefing, giftType, printingType, printingShirtType, stamp, calendarType, giftModel, linkModel);
    }

    @Override
    public String toString() {
        return "BGift{" +
                "id=" + id +
                ", briefing=" + briefing +
                ", giftType=" + giftType +
                ", printingType=" + printingType +
                ", printingShirtType=" + printingShirtType +
                ", stamp=" + stamp +
                ", calendarType=" + calendarType +
                ", giftModel='" + giftModel + '\'' +
                ", linkModel='" + linkModel + '\'' +
                '}';
    }

}