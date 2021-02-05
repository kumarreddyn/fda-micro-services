package kumarreddyn.github.fda.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long userId;
	private String name;
	private String emailAddress;
	private String countryCode;
	private String mobileNumber;
	private String password;
	private String photoURL;
}
