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
import kumarreddyn.github.fda.catalogue.dto.FoodOutletDTO;
import kumarreddyn.github.fda.catalogue.dto.converter.FoodOutletConverter;
import kumarreddyn.github.fda.catalogue.entity.FoodOutlet;
import kumarreddyn.github.fda.catalogue.feign.FileServerProxy;
import kumarreddyn.github.fda.catalogue.respository.FoodOutletRepository;
import kumarreddyn.github.fda.catalogue.util.ResponseUtil;
import kumarreddyn.github.fda.catalogue.util.SecurityUtil;

@Service
public class FoodOutletService {

	private final FoodOutletRepository foodOutletRepository;
	private final FoodOutletConverter foodOutletConverter;
	private final FileServerProxy fileServerProxy;
	private final SecurityUtil securityUtil;
	private final ResponseUtil responseUtil;
	
	public FoodOutletService(FoodOutletRepository foodOutletRepository,
			FoodOutletConverter foodOutletConverter, FileServerProxy fileServerProxy,
			SecurityUtil securityUtil, ResponseUtil responseUtil) {
		this.foodOutletRepository = foodOutletRepository;
		this.foodOutletConverter = foodOutletConverter;
		this.fileServerProxy = fileServerProxy;
		this.securityUtil = securityUtil;
		this.responseUtil = responseUtil;
	}

	public Optional<FoodOutlet> findFoodOutletById(Long foodOutletId){
		return foodOutletRepository.findById(foodOutletId);
	}
	
	public Set<FoodOutlet> findByActive(boolean active){
		return foodOutletRepository.findByActive(active);
	}
	
	public FoodOutlet toFoodOutlet(Optional<FoodOutlet> foodOutletOptional, FoodOutletDTO foodOutletDTO) {
		return foodOutletConverter.toFoodOutlet(foodOutletOptional, foodOutletDTO);
	}
	
	public FoodOutletDTO toFoodOutletDTO(FoodOutlet foodOutlet) {
		return foodOutletConverter.toFoodOutletDTO(foodOutlet);
	}
	
	public List<FoodOutletDTO> toFoodOutletDTOSet(Set<FoodOutlet> foodOutlets){
		return foodOutletConverter.toFoodOutletDTOList(foodOutlets);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, FoodOutletDTO foodOutletDTO, MultipartFile photo) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<FoodOutlet> foodOutletOptional = Optional.empty();
			FoodOutlet foodOutlet = toFoodOutlet(foodOutletOptional, foodOutletDTO);
			foodOutlet = save(request, foodOutlet);
			if(null != photo) {
				String filePath = getFileUploadPath(foodOutlet, FileConstants.PHOTO_FOLDER);
				String photoURL = fileServerProxy.uploadFile(filePath, photo);
				foodOutlet.setPhotoURL(photoURL);
			}
			foodOutlet = update(request, foodOutlet);
			dataMap.put(DataConstants.FOOD_OUTLET, toFoodOutletDTO(foodOutlet));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_SAVED);
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_SAVED);
		}
	}
	
	public FoodOutlet save(HttpServletRequest request, FoodOutlet foodOutlet) {
		foodOutlet.setActive(true);
		foodOutlet.setCreatedDate(new Date());
		foodOutlet.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return foodOutletRepository.save(foodOutlet);
	}

	public ResponseEntity<Object> update(HttpServletRequest request, FoodOutletDTO foodOutletDTO, MultipartFile photo) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<FoodOutlet> foodOutletOptional = findFoodOutletById(foodOutletDTO.getFoodOutletId());
			if(foodOutletOptional.isPresent()) {
				FoodOutlet foodOutlet = toFoodOutlet(foodOutletOptional, foodOutletDTO);
				foodOutlet = update(request, foodOutlet);
				if(null != photo) {
					String filePath = getFileUploadPath(foodOutlet, FileConstants.PHOTO_FOLDER);
					String photoURL = fileServerProxy.uploadFile(filePath, photo);
					foodOutlet.setPhotoURL(photoURL);
				}
				foodOutlet = update(request, foodOutlet);
				dataMap.put(DataConstants.FOOD_OUTLET, toFoodOutletDTO(foodOutlet));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_UPDATED);
		}
	}
	
	public FoodOutlet update(HttpServletRequest request, FoodOutlet foodOutlet) {
		foodOutlet.setUpdatedDate(new Date());
		foodOutlet.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return foodOutletRepository.save(foodOutlet);
	}


	public ResponseEntity<Object> delete(HttpServletRequest request, FoodOutletDTO foodOutletDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<FoodOutlet> foodOutletOptional = findFoodOutletById(foodOutletDTO.getFoodOutletId());
			if(foodOutletOptional.isPresent()) {
				FoodOutlet foodOutlet = foodOutletOptional.get();
				foodOutlet.setActive(false);
				foodOutlet = update(request, foodOutlet);
				dataMap.put(DataConstants.FOOD_OUTLET, toFoodOutletDTO(foodOutlet));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_FOUND);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLET_NOT_DELETED);
		}
	}
	
	private String getFileUploadPath(FoodOutlet foodOutlet, String folderName) {
		return FileConstants.FILE_PATH_SEPERATOR + ApplicationConstants.CATALOGUE_SERVICE + FileConstants.FILE_PATH_SEPERATOR 
				+ foodOutlet.getFoodOutletId() + "-" + foodOutlet.getName() + FileConstants.FILE_PATH_SEPERATOR 
				+ folderName + FileConstants.FILE_PATH_SEPERATOR;
	}

	public ResponseEntity<Object> getAllFoodOutlets() {
		Map<String, Object> dataMap = new HashMap<>();
		Set<FoodOutlet> foodOutlets = findByActive(true);
		dataMap.put(DataConstants.FOOD_OUTLETS, toFoodOutletDTOSet(foodOutlets));
		return responseUtil.generateResponse(dataMap, RestServiceConstants.FOOD_OUTLETS_FOUND);
	}
	
}
