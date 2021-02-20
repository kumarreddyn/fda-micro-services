package kumarreddyn.github.fda.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kumarreddyn.github.fda.user.constants.ApplicationConstants;
import kumarreddyn.github.fda.user.constants.DataConstants;
import kumarreddyn.github.fda.user.constants.FileConstants;
import kumarreddyn.github.fda.user.constants.RestServiceConstants;
import kumarreddyn.github.fda.user.dto.LoginDTO;
import kumarreddyn.github.fda.user.dto.UserDTO;
import kumarreddyn.github.fda.user.dto.converter.UserConverter;
import kumarreddyn.github.fda.user.entity.User;
import kumarreddyn.github.fda.user.feign.FileServerProxy;
import kumarreddyn.github.fda.user.repository.UserRepository;
import kumarreddyn.github.fda.user.util.EncryptDecryptUtil;
import kumarreddyn.github.fda.user.util.ResponseUtil;
import kumarreddyn.github.fda.user.util.SecurityUtil;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ResponseUtil responseUtil;
	private final UserConverter userConverter;
	private final FileServerProxy fileServerProxy;
	private final SecurityUtil securityUtil;
	private final EncryptDecryptUtil encryptDecryptUtil;
	
	public UserService(UserRepository userRepository,
			ResponseUtil responseUtil, UserConverter userConverter, 
			FileServerProxy fileServerProxy, SecurityUtil securityUtil,
			EncryptDecryptUtil encryptDecryptUtil) {
		this.userRepository = userRepository;
		this.responseUtil = responseUtil;
		this.userConverter = userConverter;
		this.fileServerProxy = fileServerProxy;
		this.securityUtil = securityUtil;
		this.encryptDecryptUtil = encryptDecryptUtil;
	}

	public Optional<User> findUserById(Long userId){
		return userRepository.findById(userId);
	}
	
	public User toUser(Optional<User> userOptional, UserDTO userDTO) {
		return userConverter.toUser(userOptional, userDTO);
	}
	
	public UserDTO toUserDTO(User user) {
		return userConverter.toUserDTO(user);
	}
	
	public Set<UserDTO> toUserDTOSet(Set<User> users){
		return userConverter.toUserDTOSet(users);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, UserDTO userDTO, MultipartFile photo) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = Optional.empty();
			User user = toUser(userOptional, userDTO);
			user.setActive(true);
			user = save(request, user);
			if(null != photo) {
				String filePath = getFileUploadPath(user, FileConstants.PHOTO_FOLDER);
				String photoURL = fileServerProxy.uploadFile(filePath, photo);
				user.setPhotoURL(photoURL);
			}
			user = update(request, user);
			dataMap.put(DataConstants.USER, toUserDTO(user));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_SAVED);
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_SAVED);
		}
	}

	public User save(HttpServletRequest request, User user) {
		user.setCreatedDate(new Date());
		user.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return userRepository.save(user);
	}
	
	public ResponseEntity<Object> update(HttpServletRequest request, UserDTO userDTO, MultipartFile photo) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = findUserById(userDTO.getUserId());
			if(userOptional.isPresent()) {
				User user = toUser(userOptional, userDTO);
				user = update(request, user);
				if(null != photo) {
					String filePath = getFileUploadPath(user, FileConstants.PHOTO_FOLDER);
					String photoURL = fileServerProxy.uploadFile(filePath, photo);
					user.setPhotoURL(photoURL);
				}
				user = update(request, user);
				dataMap.put(DataConstants.USER, toUserDTO(user));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_UPDATED);
		}
	}
	
	public User update(HttpServletRequest request, User user) {
		user.setUpdatedDate(new Date());
		user.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return userRepository.save(user);
	}

	public ResponseEntity<Object> delete(HttpServletRequest request, UserDTO userDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = findUserById(userDTO.getUserId());
			if(userOptional.isPresent()) {
				User user = userOptional.get();
				user.setActive(false);
				update(request, user);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_DELETED);
		}
	}
	
	private String getFileUploadPath(User user, String folderName) {
		return FileConstants.FILE_PATH_SEPERATOR + ApplicationConstants.USER_SERVICE 
				+ FileConstants.FILE_PATH_SEPERATOR + user.getUserId() + "-" + user.getName() 
				+ FileConstants.FILE_PATH_SEPERATOR + folderName + FileConstants.FILE_PATH_SEPERATOR;
	}

	public ResponseEntity<Object> login(HttpServletRequest request, LoginDTO loginDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = userRepository
					.findByMobileNumberAndPassword(loginDTO.getMobileNumber(), 
							encryptDecryptUtil.encrypt(loginDTO.getPassword()));
			if(userOptional.isPresent()) {
				String authToken = securityUtil.generateAuthenticationToken(userOptional.get());
				dataMap.put(DataConstants.X_AUTH_TOKEN, authToken);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_LOGGED_IN);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_FOUND);
		}
	}
}
