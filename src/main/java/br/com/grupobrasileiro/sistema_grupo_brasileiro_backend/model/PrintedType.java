package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tb_Printed_Types")
public class PrintedType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      PrintedType obj = (PrintedType) o;
      return Objects.equals(id, obj.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    @Override
    public String toString() {
      return "PrintedType{" + "id=" + id + '}';
    }
}
