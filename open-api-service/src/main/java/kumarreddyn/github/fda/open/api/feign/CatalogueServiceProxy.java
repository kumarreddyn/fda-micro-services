package kumarreddyn.github.fda.open.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogue-service")
public interface CatalogueServiceProxy {

	@GetMapping(path = "/food-outlet/get-all-food-outlets", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFoodOutlets();

	@GetMapping(path = "/food-item/{foodOutletId}/get-all-food-items", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllFoodItems(@PathVariable final Long foodOutletId);
	
}
