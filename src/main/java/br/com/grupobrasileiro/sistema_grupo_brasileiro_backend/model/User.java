package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Users")
public class User implements UserDetails {
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
    
    @Column(name = "status", nullable = false)
    private Boolean status;

    public User(String name, String lastname, String phonenumber, String sector, String occupation, String nop, String email, String password, Integer role, Boolean status) {
        this.name = name;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.sector = sector;
        this.occupation = occupation;
        this.nop = nop;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
    


    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == RoleEnum.ROLE_SUPERVISOR.getCode()) return List.of(new SimpleGrantedAuthority("ROLE_SUPERVISOR"), new SimpleGrantedAuthority("ROLE_CLIENT"));
		else return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
	}

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

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
}
