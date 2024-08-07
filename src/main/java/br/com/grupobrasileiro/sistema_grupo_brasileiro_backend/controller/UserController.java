package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UserView> save(@Valid @RequestBody UserForm dto) throws Exception {
		UserView view = userService.save(dto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(view);
	}
	
	@GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERVISOR') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserProfileView> getUserProfile(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        UserProfileView userProfile = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfile);
    }
	
	@PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserForm form, @AuthenticationPrincipal UserDetails userDetails) {
        User updatedUser = userService.updateUser(id, form);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

}
