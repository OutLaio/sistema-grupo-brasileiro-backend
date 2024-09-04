package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tb_BGifts")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_version")
    private Version version;

    @ManyToOne
    @JoinColumn(name = "id_printing_type")
    private PrintingType printingType;

    @ManyToOne
    @JoinColumn(name = "id_printing_shirt_type")
    private PrintingShirtType printingShirtType;

    @ManyToOne
    @JoinColumn(name = "id_printing_location")
    private PrintingLocation printingLocation;

    @ManyToOne
    @JoinColumn(name = "id_calendar_type")
    private CalendarType calendarType;

    @ManyToOne
    @JoinColumn(name = "id_obj_type")
    private GiftType objType;

    private String descriptionGift;
    private String linkModel;

    
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Gift obj = (Gift) o;
      return Objects.equals(id, obj.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    @Override
    public String toString() {
      return "Gift{" + "id=" + id + '}';
    }
}
