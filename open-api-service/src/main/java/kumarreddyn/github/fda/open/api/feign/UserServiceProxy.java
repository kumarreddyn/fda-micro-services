package kumarreddyn.github.fda.open.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceProxy {

	@PostMapping(path = "/user/register", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Object> register(@RequestParam String user);

	@PostMapping(path = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Object> login(@RequestParam String login);
	
}
