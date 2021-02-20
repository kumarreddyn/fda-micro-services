package kumarreddyn.github.fda.open.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kumarreddyn.github.fda.open.api.service.OpenAPIService;

@RestController
public class OpenAPIController {
	
	private final OpenAPIService openAPIService; 
	
	@Autowired
	public OpenAPIController(final OpenAPIService openAPIService) {
		this.openAPIService = openAPIService;
	}
	
	@GetMapping(path = "/check-services-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> checkServicesStatus() {
		return openAPIService.checkServicesStatus();
	}
	
	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestParam String user) {
		return openAPIService.register(request, user);
	}
	
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(HttpServletRequest request, @RequestParam String login) {
		return openAPIService.login(request, login);
	}
	
	@GetMapping(path = "/get-all-food-outlets", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFoodOutlets() {
		return openAPIService.getAllFoodOutlets();
	}
	
	@GetMapping(path = "/{foodOutletId}/get-all-food-items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFoodItems(@PathVariable final Long foodOutletId) {
		return openAPIService.getAllFoodItems(foodOutletId);
	}

}
