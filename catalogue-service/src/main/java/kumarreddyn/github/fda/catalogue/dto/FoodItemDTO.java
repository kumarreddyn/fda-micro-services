package kumarreddyn.github.fda.catalogue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemDTO {

	private Long foodItemId;
	private String name;
	private String description;
	private Double price;
	private String photoURL;
	private Long foodOutletId;
	
}
