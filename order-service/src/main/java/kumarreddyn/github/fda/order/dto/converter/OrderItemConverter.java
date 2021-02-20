package kumarreddyn.github.fda.order.dto.converter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.order.dto.OrderItemDTO;
import kumarreddyn.github.fda.order.entity.OrderItem;

@Component
public class OrderItemConverter {
	
	public OrderItemConverter() {
		// TODO Auto-generated constructor stub
	}
	
	public OrderItem toOrderItem(Optional<OrderItem> orderItemOptional, OrderItemDTO orderItemDTO) {
		OrderItem orderItem = new OrderItem();
		if(orderItemOptional.isPresent()) {
			orderItem = orderItemOptional.get();
		}
		orderItem.setFoodItemId(orderItemDTO.getFoodItemId());
		orderItem.setItemPrice(orderItemDTO.getItemPrice());
		orderItem.setQuantity(orderItemDTO.getQuantity());
		orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
		
		
		return orderItem;
	}
	
	public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setFoodItemId(orderItem.getFoodItemId());
		orderItemDTO.setItemPrice(orderItem.getItemPrice());
		orderItemDTO.setQuantity(orderItem.getQuantity());
		orderItemDTO.setTotalPrice(orderItem.getTotalPrice());
		orderItemDTO.setOrderId(orderItem.getOrder().getOrderId());
		return orderItemDTO;
	}
	
	public Set<OrderItemDTO> toOrderItemDTOSet(Set<OrderItem> orderItems){
		return orderItems.stream().map(this::toOrderItemDTO).collect(Collectors.toSet());
	}
	
}
