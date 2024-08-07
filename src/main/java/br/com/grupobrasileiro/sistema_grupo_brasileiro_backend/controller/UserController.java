package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	@PostMapping
//	public ResponseEntity<UserView> save(@Valid @RequestBody UserForm dto) throws Exception {
//		UserView view = userService.save(dto);
//	    return ResponseEntity.status(HttpStatus.CREATED).body(view);
//	}
	
    @GetMapping("/byRole")
    public ResponseEntity<Page<UserView>> getUsersByRole(
            @RequestParam Integer role,
            @RequestParam(defaultValue = "0") Integer page,
      		@RequestParam(value = "direction", defaultValue = "ASC" ) String direction,
    		@RequestParam(value = "orderBy", defaultValue = "name" ) String orderBy,
            @RequestParam(defaultValue = "10") int size) {
        
    	PageRequest pageRequest  = PageRequest.of(page, size, Direction.valueOf(direction),  orderBy);
        Page<UserView> usersPage = userService.getUsersByRole(role, pageRequest);
        
        return ResponseEntity.ok(usersPage);
    }
}
