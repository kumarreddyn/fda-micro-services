package kumarreddyn.github.fda.user.dto.converter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.user.dto.UserDTO;
import kumarreddyn.github.fda.user.entity.User;

@Component
public class UserConverter {

	public User toUser(Optional<User> userOptional, UserDTO userDTO) {
		User user = new User();
		if(userOptional.isPresent()) {
			user = userOptional.get();
		}
		
		return user;
	}
	
	public UserDTO toUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		
		return userDTO;
	}
	
	public Set<UserDTO> toUserDTOList(Set<User> users){
		return users.stream().map(this::toUserDTO).collect(Collectors.toSet());
	}
}
