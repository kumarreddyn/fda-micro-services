package kumarreddyn.github.fda.user.dto.converter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.user.dto.UserDTO;
import kumarreddyn.github.fda.user.entity.User;
import kumarreddyn.github.fda.user.util.EncryptDecryptUtil;

@Component
public class UserConverter {

	private final EncryptDecryptUtil encryptDecryptUtil;
	
	public UserConverter(EncryptDecryptUtil encryptDecryptUtil) {
		this.encryptDecryptUtil = encryptDecryptUtil;
	}
	
	public User toUser(Optional<User> userOptional, UserDTO userDTO) {
		User user = new User();
		if(userOptional.isPresent()) {
			user = userOptional.get();
		}
		user.setName(userDTO.getName());
		user.setEmailAddress(userDTO.getEmailAddress());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setPassword(encryptDecryptUtil.encrypt(userDTO.getPassword()));
		return user;
	}
	
	public UserDTO toUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setName(user.getName());
		userDTO.setEmailAddress(user.getEmailAddress());
		userDTO.setMobileNumber(user.getMobileNumber());
		return userDTO;
	}
	
	public Set<UserDTO> toUserDTOSet(Set<User> users){
		return users.stream().map(this::toUserDTO).collect(Collectors.toSet());
	}
}
