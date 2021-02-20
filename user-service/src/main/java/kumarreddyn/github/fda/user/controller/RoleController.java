package kumarreddyn.github.fda.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import kumarreddyn.github.fda.user.dto.RoleDTO;
import kumarreddyn.github.fda.user.service.RoleService;

@RestController
@RequestMapping(path = "/role")
public class RoleController {
	
	private final RoleService roleService;
	
	public RoleController(RoleService roleService, ObjectMapper objectMapper) {
		this.roleService = roleService;
	}

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody RoleDTO roleDTO) {
		return roleService.save(request, roleDTO);
	}
	
	@PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody RoleDTO roleDTO) {
		return roleService.update(request, roleDTO);
	}

	@PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody RoleDTO roleDTO) {
		return roleService.delete(request, roleDTO);
	}
}
