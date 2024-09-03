package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import java.util.Date;
import java.util.Objects;

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
@Entity(name = "Tb_Versions")
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    private String title;
    private String feedback;
    private Date begin;
    private Date expectedDone;
    private Date realDone;
    private Integer numVersion;
    private String productLink;
    private Boolean clientApproved;
    private Boolean supervisorApproved;
    private String detailedDescription;
    private String collaborator;
    private String otherCompany;
    
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Version obj = (Version) o;
      return Objects.equals(id, obj.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    @Override
    public String toString() {
      return "Version{" + "id=" + id + '}';
    }
}
