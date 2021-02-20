package kumarreddyn.github.fda.user.dto.converter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.user.dto.AddressDTO;
import kumarreddyn.github.fda.user.entity.Address;

@Component
public class AddressConverter {

	public Address toAddress(Optional<Address> addressOptional, AddressDTO addressDTO) {
		Address address = new Address();
		if(addressOptional.isPresent()) {
			address = addressOptional.get();
		}
		address.setAddress(addressDTO.getAddress());
		address.setLandmark(addressDTO.getLandmark());
		address.setCity(addressDTO.getCity());
		address.setPincode(addressDTO.getPincode());
		address.setMobileNumber(addressDTO.getMobileNumber());
		return address;
	}
	
	public AddressDTO toAddressDTO(Address address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setAddress(address.getAddress());
		addressDTO.setLandmark(address.getLandmark());
		addressDTO.setCity(address.getCity());
		addressDTO.setPincode(address.getPincode());
		addressDTO.setMobileNumber(address.getMobileNumber());
		return addressDTO;
	}
	
	public List<AddressDTO> toAddressDTOList(Set<Address> addresses){
		return addresses.stream().map(this::toAddressDTO).collect(Collectors.toList());
	}
	
}
