package kumarreddyn.github.fda.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kumarreddyn.github.fda.user.constants.DataConstants;
import kumarreddyn.github.fda.user.constants.RestServiceConstants;
import kumarreddyn.github.fda.user.dto.UserDTO;
import kumarreddyn.github.fda.user.dto.converter.UserConverter;
import kumarreddyn.github.fda.user.entity.User;
import kumarreddyn.github.fda.user.repository.UserRepository;
import kumarreddyn.github.fda.user.util.ResponseUtil;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ResponseUtil responseUtil;
	private final UserConverter userConverter;
	
	public UserService(UserRepository userRepository,
			ResponseUtil responseUtil, UserConverter userConverter) {
		this.userRepository = userRepository;
		this.responseUtil = responseUtil;
		this.userConverter = userConverter;
	}

	public Optional<User> findUserById(Long userId){
		return userRepository.findById(userId);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, UserDTO userDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = Optional.empty();
			User user = userConverter.toUser(userOptional, userDTO);
			user.setActive(true);
			user = save(user);
			dataMap.put(DataConstants.USER, user);
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_SAVED);
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_SAVED);
		}
	}

	public User save(User user) {
		user.setCreatedDate(new Date());
		return userRepository.save(user);
	}
	
	public ResponseEntity<Object> update(HttpServletRequest request, UserDTO userDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = findUserById(userDTO.getUserId());
			User user = userConverter.toUser(userOptional, userDTO);
			user = save(user);
			dataMap.put(DataConstants.USER, user);
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_SAVED);
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_SAVED);
		}
	}
	
	public User update(User user) {
		user.setUpdatedDate(new Date());
		return userRepository.save(user);
	}

	public ResponseEntity<Object> delete(HttpServletRequest request, UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
