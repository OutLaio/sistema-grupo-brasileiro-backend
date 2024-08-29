package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String feedback;
    
    @Column(nullable = false)
    private Date begin;

    @Column(nullable = false)
    private Date end;
    
    @Column(name = "num_version", nullable = false)
    private Integer numVersion;

    @Column(name = "product_link", nullable = false)
    private String productLink;
    
    @Column(name = "client_approve", nullable = false)
    private Boolean clientApproved;
    
    @Column(name = "supervisor_approve", nullable = false)
    private Boolean supervisorApproved;
    
    @OneToMany(mappedBy = "employee_id")
    private Set<Employee> collaborators = new HashSet<>();
    
    @OneToMany(mappedBy = "project_id")
    private Set<Project> projects = new HashSet<>();
    
    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      Version employee = (Version) o;
      return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

    @Override
    public String toString() {
      return "Employee{" + "id=" + id + '}';
    }
}
