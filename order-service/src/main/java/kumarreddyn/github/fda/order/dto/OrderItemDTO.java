package kumarreddyn.github.fda.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

	private Long orderItemId;
	private Long foodItemId;
	private Double itemPrice;
	private Integer quantity;
	private Double totalPrice;
	private Long orderId;
	
}
