package kumarreddyn.github.fda.catalogue.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kumarreddyn.github.fda.catalogue.dto.FoodOutletDTO;
import kumarreddyn.github.fda.catalogue.service.FoodOutletService;

@RestController
@RequestMapping(path = "/food-outlet")
public class FoodOutletController {
	
	private final ObjectMapper objectMapper;
	private final FoodOutletService foodOutletService;
	
	public FoodOutletController(FoodOutletService foodOutletService, ObjectMapper objectMapper) {
		this.foodOutletService = foodOutletService;
		this.objectMapper = objectMapper;
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request,
			@RequestParam String foodOutlet, @RequestParam(name="photo", required=false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
		final FoodOutletDTO foodOutletDTO = objectMapper.readValue(foodOutlet, FoodOutletDTO.class);
		return foodOutletService.save(request, foodOutletDTO, photo);
	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request,
			@RequestParam String foodOutlet, @RequestParam(name="photo", required=false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
		final FoodOutletDTO foodOutletDTO = objectMapper.readValue(foodOutlet, FoodOutletDTO.class);
		return foodOutletService.update(request, foodOutletDTO, photo);
	}

	@PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody FoodOutletDTO foodOutletDTO) {
		return foodOutletService.delete(request, foodOutletDTO);
	}
	
	@GetMapping(path = "/get-all-food-outlets", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFoodOutlets() {
		return foodOutletService.getAllFoodOutlets();
	}
}
