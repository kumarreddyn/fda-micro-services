package kumarreddyn.github.fda.open.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
