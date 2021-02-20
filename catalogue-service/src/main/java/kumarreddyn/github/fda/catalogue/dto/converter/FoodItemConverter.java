package kumarreddyn.github.fda.catalogue.dto.converter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.catalogue.dto.FoodItemDTO;
import kumarreddyn.github.fda.catalogue.entity.FoodItem;

@Component
public class FoodItemConverter {
	
	public FoodItem toFoodItem(Optional<FoodItem> foodItemOptional, FoodItemDTO foodItemDTO) {
		FoodItem foodItem = new FoodItem();
		if(foodItemOptional.isPresent()) {
			foodItem = foodItemOptional.get();
		}
		foodItem.setName(foodItemDTO.getName());
		foodItem.setDescription(foodItemDTO.getDescription());
		foodItem.setPrice(foodItemDTO.getPrice());
		return foodItem;
	}
	
	public FoodItemDTO toFoodItemDTO(FoodItem foodItem) {
		FoodItemDTO foodItemDTO = new FoodItemDTO();
		foodItemDTO.setFoodItemId(foodItem.getFoodItemId());
		foodItemDTO.setFoodOutletId(foodItem.getFoodOutlet().getFoodOutletId());
		foodItemDTO.setName(foodItem.getName());
		foodItemDTO.setDescription(foodItem.getDescription());
		foodItemDTO.setPrice(foodItem.getPrice());
		foodItemDTO.setPhotoURL(foodItem.getPhotoURL());
		return foodItemDTO;
	}
	
	public List<FoodItemDTO> toFoodItemDTOList(Set<FoodItem> foodItems){
		return foodItems.stream().map(this::toFoodItemDTO).collect(Collectors.toList());
	}
}
