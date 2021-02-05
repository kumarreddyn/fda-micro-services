package kumarreddyn.github.fda.zuul.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	CustomAuthenticationFilter customAuthenticationFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(new CustomAuthenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// disabling csrf here. should be enabled in prod
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/open-api-service/**").permitAll()
			.antMatchers("/api/file-server/get-file/**").permitAll()
			.antMatchers("/**").authenticated().and()
			.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);	        
	}
	
}
