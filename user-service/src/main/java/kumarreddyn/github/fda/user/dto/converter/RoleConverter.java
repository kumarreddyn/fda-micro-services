package kumarreddyn.github.fda.user.dto.converter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.user.dto.RoleDTO;
import kumarreddyn.github.fda.user.entity.Role;

@Component
public class RoleConverter {

	public Role toRole(Optional<Role> roleOptional, RoleDTO roleDTO) {
		Role role = new Role();
		if(roleOptional.isPresent()) {
			role = roleOptional.get();
		}
		role.setCode(roleDTO.getCode());
		role.setName(roleDTO.getName());
		return role;
	}
	
	public RoleDTO toRoleDTO(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setRoleId(role.getRoleId());
		roleDTO.setCode(role.getCode());
		roleDTO.setName(role.getName());
		return roleDTO;
	}
	
	public Set<RoleDTO> toRoleDTOSet(Set<Role> roles){
		return roles.stream().map(this::toRoleDTO).collect(Collectors.toSet());
	}
}
