package kumarreddyn.github.fda.catalogue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodOutletDTO {

	private Long foodOutletId;
	private String name;
	private String description;
	private String location;
	private String photoURL;
	
}
