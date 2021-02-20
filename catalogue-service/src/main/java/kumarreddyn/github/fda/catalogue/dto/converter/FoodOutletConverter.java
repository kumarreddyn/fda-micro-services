package kumarreddyn.github.fda.catalogue.dto.converter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.catalogue.dto.FoodOutletDTO;
import kumarreddyn.github.fda.catalogue.entity.FoodOutlet;

@Component
public class FoodOutletConverter {
	
	public FoodOutlet toFoodOutlet(Optional<FoodOutlet> foodOutletOptional, FoodOutletDTO foodOutletDTO) {
		FoodOutlet foodOutlet = new FoodOutlet();
		if(foodOutletOptional.isPresent()) {
			foodOutlet = foodOutletOptional.get();
		}
		foodOutlet.setName(foodOutletDTO.getName());
		foodOutlet.setDescription(foodOutletDTO.getDescription());
		foodOutlet.setLocation(foodOutletDTO.getLocation());
		return foodOutlet;
	}
	
	public FoodOutletDTO toFoodOutletDTO(FoodOutlet foodOutlet) {
		FoodOutletDTO foodOutletDTO = new FoodOutletDTO();
		foodOutletDTO.setFoodOutletId(foodOutlet.getFoodOutletId());
		foodOutletDTO.setName(foodOutlet.getName());
		foodOutletDTO.setDescription(foodOutlet.getDescription());
		foodOutletDTO.setLocation(foodOutlet.getLocation());
		foodOutletDTO.setPhotoURL(foodOutlet.getPhotoURL());
		return foodOutletDTO;
	}
	
	public List<FoodOutletDTO> toFoodOutletDTOList(Set<FoodOutlet> foodOutlets){
		return foodOutlets.stream().sorted(Comparator.comparing(FoodOutlet::getName)).map(this::toFoodOutletDTO).collect(Collectors.toList());
	}
}
