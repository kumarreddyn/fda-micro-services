package kumarreddyn.github.fda.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kumarreddyn.github.fda.user.dto.AddressDTO;
import kumarreddyn.github.fda.user.service.AddressService;

@RestController
@RequestMapping(path = "/address")
public class AddressController {
	
	private final AddressService addressService;
	
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody AddressDTO addressDTO) {
		return addressService.save(request, addressDTO);
	}
	
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody AddressDTO addressDTO) {
		return addressService.update(request, addressDTO);
	}
	
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody AddressDTO addressDTO) {
		return addressService.delete(request, addressDTO);
	}
	
	@GetMapping(path = "/{userId}/get-all-user-addresses", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllUserAddresses(HttpServletRequest request, @PathVariable final Long userId) {
		return addressService.getAllUserAddresses(userId);
	}
	
}
