package kumarreddyn.github.fda.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kumarreddyn.github.fda.user.dto.LoginDTO;
import kumarreddyn.github.fda.user.dto.UserDTO;
import kumarreddyn.github.fda.user.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {
	
	private final ObjectMapper objectMapper;
	private final UserService userService;
	
	public UserController(UserService userService, ObjectMapper objectMapper) {
		this.userService = userService;
		this.objectMapper = objectMapper;
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request,
			@RequestParam String user, @RequestParam(name="photo", required=false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
		final UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
		return userService.save(request, userDTO, photo);
	}
	
	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> register(HttpServletRequest request, @RequestParam String user) throws JsonMappingException, JsonProcessingException {
		final UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
		return userService.save(request, userDTO, null);
	}
	
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(HttpServletRequest request, @RequestParam String login) throws JsonMappingException, JsonProcessingException {
		final LoginDTO loginDTO = objectMapper.readValue(login, LoginDTO.class);
		return userService.login(request, loginDTO);
	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request,
			@RequestParam String user, @RequestParam(name="photo", required=false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
		final UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
		return userService.update(request, userDTO, photo);
	}

	@PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		return userService.delete(request, userDTO);
	}
}
