package kumarreddyn.github.fda.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import kumarreddyn.github.fda.user.constants.DataConstants;
import kumarreddyn.github.fda.user.constants.RestServiceConstants;
import kumarreddyn.github.fda.user.dto.RoleDTO;
import kumarreddyn.github.fda.user.dto.converter.RoleConverter;
import kumarreddyn.github.fda.user.entity.Role;
import kumarreddyn.github.fda.user.repository.RoleRepository;
import kumarreddyn.github.fda.user.util.ResponseUtil;
import kumarreddyn.github.fda.user.util.SecurityUtil;

@Service
public class RoleService {

	private final RoleRepository roleRepository;
	private final ResponseUtil responseUtil;
	private final RoleConverter roleConverter;
	private final SecurityUtil securityUtil;
	
	public RoleService(RoleRepository userRepository,
			ResponseUtil responseUtil, RoleConverter roleConverter, 
			SecurityUtil securityUtil) {
		this.roleRepository = userRepository;
		this.responseUtil = responseUtil;
		this.roleConverter = roleConverter;
		this.securityUtil = securityUtil;
	}

	public Optional<Role> findRoleById(Long roleId){
		return roleRepository.findById(roleId);
	}
	
	public Role toRole(Optional<Role> roleOptional, RoleDTO roleDTO) {
		return roleConverter.toRole(roleOptional, roleDTO);
	}
	
	public RoleDTO toRoleDTO(Role role) {
		return roleConverter.toRoleDTO(role);
	}
	
	public Set<RoleDTO> toRoleDTOSet(Set<Role> roles){
		return roleConverter.toRoleDTOSet(roles);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Role> roleOptional = Optional.empty();
			Role role = toRole(roleOptional, roleDTO);
			role.setActive(true);
			role = save(request, role);
			dataMap.put(DataConstants.ROLE, toRoleDTO(role));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_SAVED);
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_NOT_SAVED);
		}
	}

	public Role save(HttpServletRequest request, Role role) {
		role.setCreatedDate(new Date());
		role.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return roleRepository.save(role);
	}
	
	public ResponseEntity<Object> update(HttpServletRequest request, RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Role> roleOptional = findRoleById(roleDTO.getRoleId());
			if(roleOptional.isPresent()) {
				Role role = toRole(roleOptional, roleDTO);
				role = update(request, role);
				dataMap.put(DataConstants.ROLE, toRoleDTO(role));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_NOT_UPDATED);
		}
	}
	
	public Role update(HttpServletRequest request, Role role) {
		role.setUpdatedDate(new Date());
		role.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return roleRepository.save(role);
	}

	public ResponseEntity<Object> delete(HttpServletRequest request, RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Role> roleOptional = findRoleById(roleDTO.getRoleId());
			if(roleOptional.isPresent()) {
				Role role = roleOptional.get();
				role.setActive(false);
				update(request, role);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_NOT_DELETED);
		}
	}

}
