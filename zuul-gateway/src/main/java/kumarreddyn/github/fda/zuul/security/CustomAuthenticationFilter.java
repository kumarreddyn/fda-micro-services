package kumarreddyn.github.fda.zuul.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter{

	@Value("${jwt.secret.key}") 
	String jwtSecretKey;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String xAuthToken = request.getHeader("X_AUTH_TOKEN");
		if(null != xAuthToken) {
			Claims claims = decodeToken(xAuthToken);
	        if(null != claims) {        	
	            Authentication auth = new UsernamePasswordAuthenticationToken(AuthorityUtils.createAuthorityList("ROLE_USER"), claims.getSubject());                    
	            SecurityContextHolder.getContext().setAuthentication(auth);                        
	            filterChain.doFilter(request, response);                        
	        }else {        	
	        	throw new SecurityException("Bad Credentials");
	        }
		}else {
			throw new SecurityException("Token is null");
		}
		
	}

	private Claims decodeToken(String xAuthToken) throws JwtException, SignatureException {    	
    	Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey.getBytes())
                .parseClaimsJws(xAuthToken)
                .getBody();    	
    	return claims;
    }

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
        return (path.startsWith("/api/open-api-service/") || path.startsWith("/api/file-server/get-file/"));
	}
	
	
}
