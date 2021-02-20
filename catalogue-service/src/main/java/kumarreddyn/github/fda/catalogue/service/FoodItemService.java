package kumarreddyn.github.fda.catalogue.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kumarreddyn.github.fda.catalogue.constants.ApplicationConstants;
import kumarreddyn.github.fda.catalogue.constants.DataConstants;
import kumarreddyn.github.fda.catalogue.constants.FileConstants;
import kumarreddyn.github.fda.catalogue.constants.RestServiceConstants;
import kumarreddyn.github.fda.catalogue.dto.FoodItemDTO;
import kumarreddyn.github.fda.catalogue.dto.converter.FoodItemConverter;
import kumarreddyn.github.fda.catalogue.entity.FoodItem;
import kumarreddyn.github.fda.catalogue.entity.FoodOutlet;
import kumarreddyn.github.fda.catalogue.feign.FileServerProxy;
import kumarreddyn.github.fda.catalogue.respository.FoodItemRepository;
import kumarreddyn.github.fda.catalogue.util.ResponseUtil;
import kumarreddyn.github.fda.catalogue.util.SecurityUtil;

@Service
public class FoodItemService {

	private final FoodItemRepository foodItemRepository;
	private final FoodItemConverter foodItemConverter;
	private final FileServerProxy fileServerProxy;
	private final SecurityUtil securityUtil;
	private final ResponseUtil responseUtil;
	private final FoodOutletService foodOutletService;
	
	public FoodItemService(FoodItemRepository foodItemRepository,
			FoodItemConverter foodItemConverter, FileServerProxy fileServerProxy,
			SecurityUtil securityUtil, ResponseUtil responseUtil,
			FoodOutletService foodOutletService) {
		this.foodItemRepository = foodItemRepository;
		this.foodItemConverter = foodItemConverter;
		this.fileServerProxy = fileServerProxy;
		this.securityUtil = securityUtil;
		this.responseUtil = responseUtil;
		this.foodOutletService = foodOutletService;
	}

	public Optional<FoodItem> findFoodItemById(Long foodItemId){
		return foodItemRepository.findById(foodItemId);
	}
	
	public FoodItem toFoodItem(Optional<FoodItem> foodItemOptional, FoodItemDTO foodItemDTO) {
		return foodItemConverter.toFoodItem(foodItemOptional, foodItemDTO);
	}
	
	public FoodItemDTO toFoodItem(FoodItem foodItem) {
		return foodItemConverter.toFoodItemDTO(foodItem);
	}
	
	public List<FoodItemDTO> toFoodItemDTOList(Set<FoodItem> foodItems){
		return foodItemConverter.toFoodItemDTOList(foodItems);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, FoodItemDTO foodItemDTO, MultipartFile photo) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<FoodOutlet> foodOutletOptional = foodOutletService.findFoodOutletById(foodItemDTO.getFoodOutletId());
			if(foodOutletOptional.isPresent()) {
				Optional<FoodItem> foodItemOptional = Optional.empty();
				FoodItem foodItem = toFoodItem(foodItemOptional, foodItemDTO);
				foodItem.setFoodOutlet(foodOutletOptional.get());
				foodItem = save(request, foodItem);
				if(null != photo) {
					String filePath = getFileUploadPath(foodItem, FileConstants.PHOTO_FOLDER);
					String photoURL = fileServerProxy.uploadFile(filePath, photo);
					foodItem.setPhotoURL(photoURL);
				}
				foodItem = update(request, foodItem);
				dataMap.put(DataConstants.FOOD_ITEM, toFoodItem(foodItem));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_SAVED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_FOUND);	
			}
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_NOT_SAVED);
		}
	}
	
	public FoodItem save(HttpServletRequest request, FoodItem foodItem) {
		foodItem.setActive(true);
		foodItem.setCreatedDate(new Date());
		foodItem.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return foodItemRepository.save(foodItem);
	}

	public ResponseEntity<Object> update(HttpServletRequest request, FoodItemDTO foodItemDTO, MultipartFile photo) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<FoodItem> foodItemOptional = findFoodItemById(foodItemDTO.getFoodItemId());
			if(foodItemOptional.isPresent()) {
				FoodItem foodItem = toFoodItem(foodItemOptional, foodItemDTO);
				foodItem = update(request, foodItem);
				if(null != photo) {
					String filePath = getFileUploadPath(foodItem, FileConstants.PHOTO_FOLDER);
					String photoURL = fileServerProxy.uploadFile(filePath, photo);
					foodItem.setPhotoURL(photoURL);
				}
				foodItem = update(request, foodItem);
				dataMap.put(DataConstants.FOOD_ITEM, toFoodItem(foodItem));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_NOT_UPDATED);
		}
	}
	
	public FoodItem update(HttpServletRequest request, FoodItem foodItem) {
		foodItem.setUpdatedDate(new Date());
		foodItem.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return foodItemRepository.save(foodItem);
	}

	public ResponseEntity<Object> delete(HttpServletRequest request, FoodItemDTO foodItemDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<FoodItem> foodItemOptional = findFoodItemById(foodItemDTO.getFoodItemId());
			if(foodItemOptional.isPresent()) {
				FoodItem foodItem = foodItemOptional.get();
				foodItem.setActive(false);
				foodItem = update(request, foodItem);
				dataMap.put(DataConstants.FOOD_ITEM, toFoodItem(foodItem));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEM_NOT_DELETED);
		}
	}
	
	private String getFileUploadPath(FoodItem foodItem, String folderName) {
		return FileConstants.FILE_PATH_SEPERATOR + ApplicationConstants.CATALOGUE_SERVICE 
				+ FileConstants.FILE_PATH_SEPERATOR + foodItem.getFoodItemId() + "-" + foodItem.getName() 
				+ FileConstants.FILE_PATH_SEPERATOR + folderName + FileConstants.FILE_PATH_SEPERATOR;
	}

	public ResponseEntity<Object> getAllFoodItems(Long foodOutletId) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<FoodOutlet> foodOutletOptional = foodOutletService.findFoodOutletById(foodOutletId);
		if(foodOutletOptional.isPresent()) {
			Set<FoodItem> foodItems = foodOutletOptional.get().getFoodItems();
			dataMap.put(DataConstants.FOOD_ITEMS, toFoodItemDTOList(foodItems));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_ITEMS_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_FOUND);
		}
	}
	
}
