package kumarreddyn.github.fda.catalogue.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kumarreddyn.github.fda.catalogue.dto.FoodItemDTO;
import kumarreddyn.github.fda.catalogue.service.FoodItemService;

@RestController
@RequestMapping(path = "/food-item")
public class FoodItemController {
	
	private final ObjectMapper objectMapper;
	private final FoodItemService foodItemService;
	
	public FoodItemController(FoodItemService foodItemService, ObjectMapper objectMapper) {
		this.foodItemService = foodItemService;
		this.objectMapper = objectMapper;
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request,
			@RequestParam String foodItem, @RequestParam(name="photo", required=false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
		final FoodItemDTO foodItemDTO = objectMapper.readValue(foodItem, FoodItemDTO.class);
		return foodItemService.save(request, foodItemDTO, photo);
	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request,
			@RequestParam String foodItem, @RequestParam(name="photo", required=false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
		final FoodItemDTO foodItemDTO = objectMapper.readValue(foodItem, FoodItemDTO.class);
		return foodItemService.update(request, foodItemDTO, photo);
	}

	@PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody FoodItemDTO foodItemDTO) {
		return foodItemService.delete(request, foodItemDTO);
	}
	
	@GetMapping(path = "/{foodOutletId}/get-all-food-items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFoodItems(@PathVariable final Long foodOutletId) {
		return foodItemService.getAllFoodItems(foodOutletId);
	}
}
