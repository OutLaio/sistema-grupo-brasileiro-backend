package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProjectUserAdderForm(
		@NotNull(message = "Role is required!") Integer id, 
		
		@NotBlank(message = "Name is required!") String name,
		
		@NotBlank(message = "Lastname is required!") String lastname,
		
		// Exemplos válidos: +55 (11) 98888-8888 / 9999-9999 / 21 98888-8888 /
		// 5511988888888
		@NotBlank(message = "Phonenumber is required!") @Pattern(regexp = "^(?:(?:\\+|00)?55\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?((?:9\\d|[2-9])\\d{3})-?(\\d{4})$", message = "Invalid phonenumber!") @Size(max = 20, message = "Phonenumber must be less than 20 characters!") String phonenumber,
		
		@NotBlank(message = "Sector is required!") String sector,
		
		@NotBlank(message = "Occupation is required!") String occupation,
		
		@NotBlank(message = "Nop is required!") String nop,
		
		@NotBlank(message = "Email is required!") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email!") @Size(max = 50, message = "Email must be less than 50 characters!") String email,
		
		@NotNull(message = "Role is required!") Integer role) {
	
	public static Set<User> converter(Set<ProjectUserAdderForm> userForms) {
        return userForms.stream()
                        .map(userForm -> {
                            User user = new User();
                            user.setId(userForm.id().longValue());
                            user.setName(userForm.name());
                            user.setLastname(userForm.lastname());
                            user.setPhonenumber(userForm.phonenumber());
                            user.setSector(userForm.sector());
                            user.setOccupation(userForm.occupation());
                            user.setNop(userForm.nop());
                            user.setEmail(userForm.email());
                            user.setRole(RoleEnum.fromCode(userForm.role()).getCode());
                            return user;
                        })
                        .collect(Collectors.toSet());
    }

}


