package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "phonenumber", nullable = false)
    private String phonenumber;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "nop", nullable = false)
    private String nop;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User usuario = (User) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + '}';
    }
}
