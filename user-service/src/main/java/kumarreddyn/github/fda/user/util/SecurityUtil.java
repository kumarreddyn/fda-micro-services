package kumarreddyn.github.fda.user.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kumarreddyn.github.fda.user.constants.DataConstants;
import kumarreddyn.github.fda.user.constants.SecurityConstants;
import kumarreddyn.github.fda.user.dto.UserDTO;
import kumarreddyn.github.fda.user.entity.Role;
import kumarreddyn.github.fda.user.entity.User;
import kumarreddyn.github.fda.user.service.UserService;

@Component
public class SecurityUtil {

	private final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	private final UserService userService;
	private final ObjectMapper objectMapper;
	
	@Value("${jwt.secret.key}") 
	String jwtSecretKey;
	
	public SecurityUtil(@Lazy UserService userService,
			ObjectMapper objectMapper) {
		this.userService = userService;
		this.objectMapper = objectMapper;
	}
	
	public Long getLoggedInMemberId(HttpServletRequest request) {
		return getLongValueFromRequest(request, SecurityConstants.LOGGED_IN_MEMBER_ID);
	}
	
	private Long getLongValueFromRequest(HttpServletRequest request, String key) {
		Long value = null;
		try {
			value = (Long) request.getAttribute(key);
		}catch (Exception e) {
			logger.error("Not able to get value from request. - {}", e.getMessage());
		} 
		return value;
	}

	public String generateAuthenticationToken(User user) {
		Long now = System.currentTimeMillis();
		return Jwts.builder()
			.setSubject(user.getMobileNumber())
			.addClaims(generateCustomClaims(user))
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + (24 * 60 * 60 * 1000)))  //1 day
			.signWith(SignatureAlgorithm.HS512, jwtSecretKey.getBytes())
			.compact();
	}
	
	private Map<String, Object> generateCustomClaims(User user) {
		Map<String, Object> customClaims = new HashMap<>();
		Set<String> memberRoles = user.getRoles().stream().map(Role::getCode).collect(Collectors.toSet());
		customClaims.put(SecurityConstants.USER_ROLES, memberRoles);
		UserDTO userDTO = userService.toUserDTO(user);
		customClaims.put(DataConstants.USER, userDTO);
		return customClaims;
	}
	
	public Claims decodeToken(String authToken){
		Claims claims = null;
		if (authToken != null) {
			claims = Jwts.parser().setSigningKey(jwtSecretKey.getBytes()).parseClaimsJws(authToken)
					.getBody();
		}
		return claims;
	}

	public String generateRefreshToken(Claims claims) {
		UserDTO userDTO = objectMapper.convertValue(claims.get(DataConstants.USER), UserDTO.class);
		Optional<User> userOptional = userService.findUserById(userDTO.getUserId());
		if(userOptional.isPresent()) {
			return generateAuthenticationToken(userOptional.get());
		}
		return null;
	}
	
}
