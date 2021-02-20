package kumarreddyn.github.fda.catalogue.util;

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

import kumarreddyn.github.fda.catalogue.constants.SecurityConstants;

@Component
public class SecurityUtil {

	private final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	private final ObjectMapper objectMapper;
	
	@Value("${jwt.secret.key}") 
	String jwtSecretKey;
	
	public SecurityUtil(ObjectMapper objectMapper) {
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
	
}
