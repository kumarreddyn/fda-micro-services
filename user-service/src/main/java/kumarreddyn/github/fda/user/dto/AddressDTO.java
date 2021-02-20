package kumarreddyn.github.fda.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

	private Long addressId;
	private String address;
	private String landmark;
	private String city;
	private String pincode;
	private String mobileNumber;
	private Long userId;
	
}
