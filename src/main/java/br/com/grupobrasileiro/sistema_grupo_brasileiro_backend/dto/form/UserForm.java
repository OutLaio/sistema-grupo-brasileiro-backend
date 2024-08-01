package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserForm(
		@NotBlank(message = "Name is required!")
        String name,
        
        @NotBlank(message = "Lastname is required!")
        String lastname,
		
        // Exemplos válidos: +55 (11) 98888-8888 / 9999-9999 / 21 98888-8888 / 5511988888888
        @NotBlank(message = "Phonenumber is required!")
		@Pattern(regexp = "/^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$/", message = "Invalid email!")
		@Size(max = 20, message = "Phonenumber must be less than 20 characters!")
		String phonenumber,
		
		@NotBlank(message = "Sector is required!")
		String sector,
		
		@NotBlank(message = "Function is required!")
		String function,
		
		@NotBlank(message = "Nop is required!")
		String nop,
		
        @NotBlank(message = "Email is required!")
		@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Invalid email!")
		@Size(max = 50, message = "Email must be less than 50 characters!")
		String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 8, message = "Password must be at least 8 characters long!")
        @Pattern(regexp = "^(?=.*[a-z]).*$", message = "Password must contain at least one lowercase letter!")
        @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase letter!")
        @Pattern(regexp = "^(?=.*\\d).*$", message = "Password must contain at least one digit!")
        @Pattern(regexp = "^(?=.*[@$!%*?&]).*$", message = "Password must contain at least one special character!")
        String password
) {

}
