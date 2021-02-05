package kumarreddyn.github.fda.user.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.user.constants.SecurityConstants;

@Component
public class SecurityUtil {

	private final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	
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
