package kumarreddyn.github.fda.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import kumarreddyn.github.fda.user.constants.DataConstants;
import kumarreddyn.github.fda.user.constants.RestServiceConstants;
import kumarreddyn.github.fda.user.dto.AddressDTO;
import kumarreddyn.github.fda.user.dto.converter.AddressConverter;
import kumarreddyn.github.fda.user.entity.Address;
import kumarreddyn.github.fda.user.entity.User;
import kumarreddyn.github.fda.user.repository.AddressRepository;
import kumarreddyn.github.fda.user.util.ResponseUtil;
import kumarreddyn.github.fda.user.util.SecurityUtil;

@Service
public class AddressService {

	private final AddressRepository addressRepository;
	private final ResponseUtil responseUtil;
	private final AddressConverter addressConverter;
	private final SecurityUtil securityUtil;
	private final UserService userService;
	
	public AddressService(AddressRepository userRepository,
			ResponseUtil responseUtil, AddressConverter roleConverter, 
			SecurityUtil securityUtil, UserService userService) {
		this.addressRepository = userRepository;
		this.responseUtil = responseUtil;
		this.addressConverter = roleConverter;
		this.securityUtil = securityUtil;
		this.userService = userService;
	}

	public Optional<Address> findAddressById(Long addressId){
		return addressRepository.findById(addressId);
	}
	
	public Address toAddress(Optional<Address> addressOptional, AddressDTO addressDTO) {
		return addressConverter.toAddress(addressOptional, addressDTO);
	}
	
	public AddressDTO toAddressDTO(Address address) {
		return addressConverter.toAddressDTO(address);
	}
	
	public List<AddressDTO> toAddressDTOList(Set<Address> addresses){
		return addressConverter.toAddressDTOList(addresses);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, AddressDTO addressDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Address> roleOptional = Optional.empty();
			Address address = toAddress(roleOptional, addressDTO);
			Optional<User> userOptional = userService.findUserById(securityUtil.getLoggedInMemberId(request));
			if(userOptional.isPresent()) {
				address = save(request, address);
				dataMap.put(DataConstants.ADDRESS, address);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_SAVED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_NOT_SAVED);
		}
	}

	public Address save(HttpServletRequest request, Address address) {
		address.setActive(true);
		address.setCreatedDate(new Date());
		address.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return addressRepository.save(address);
	}
	
	public ResponseEntity<Object> update(HttpServletRequest request, AddressDTO addressDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Address> addressOptional = findAddressById(addressDTO.getAddressId());
			if(addressOptional.isPresent()) {
				Address role = toAddress(addressOptional, addressDTO);
				role = update(request, role);
				dataMap.put(DataConstants.ADDRESS, role);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_NOT_UPDATED);
		}
	}
	
	public Address update(HttpServletRequest request, Address address) {
		address.setUpdatedDate(new Date());
		address.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return addressRepository.save(address);
	}

	public ResponseEntity<Object> delete(HttpServletRequest request, AddressDTO addressDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Address> addressOptional = findAddressById(addressDTO.getAddressId());
			if(addressOptional.isPresent()) {
				Address role = addressOptional.get();
				role.setActive(false);
				update(request, role);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_NOT_FOUND);
			}
		}catch (Exception e) {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ROLE_NOT_DELETED);
		}
	}

	public ResponseEntity<Object> getAllUserAddresses(Long userId) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<User> userOptional = userService.findUserById(userId);
		if(userOptional.isPresent()) {
			Set<Address> addresses = userOptional.get().getAddresses();
			dataMap.put(DataConstants.ADDRESSES, toAddressDTOList(addresses));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ADDRESS_LIST_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, RestServiceConstants.USER_NOT_FOUND);
		}
	}

}
