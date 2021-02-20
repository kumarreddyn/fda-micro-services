package kumarreddyn.github.fda.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

	private Long orderId;
	private String orderNumber;
	private Long foodOutletId;
	private Double totalPrice;
	private Long orderedBy;
	private String orderedDate;
	private Long addressId;
	
}
