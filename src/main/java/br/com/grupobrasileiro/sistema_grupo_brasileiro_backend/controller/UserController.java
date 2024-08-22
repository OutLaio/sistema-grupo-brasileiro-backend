package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @Cacheable("collaborators")
    @GetMapping("/collaborators")
    public ResponseEntity<Page<UserView>> getUsersCollaborators(
            @RequestParam(defaultValue = "0") Integer page,
      		@RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
    		@RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy,
            @RequestParam(defaultValue = "10") int size) {
    	
        Integer role = RoleEnum.ROLE_COLLABORATOR.getCode();
    	PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        Page<UserView> usersPage = userService.getUsersCollaborators(role, pageRequest);
        
        return ResponseEntity.ok(usersPage);
    }
	
	@Cacheable(value = "idUser", key = "#id")
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
    
	@PutMapping("/deactivate")
    @PreAuthorize("hasRole('ROLE_CLIENT') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deactivateUser(Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        userService.deactivateUser(userId);
        return ResponseEntity.noContent().build();
    }

}
