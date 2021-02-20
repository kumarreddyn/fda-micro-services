package kumarreddyn.github.fda.order.dto.converter;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kumarreddyn.github.fda.order.dto.OrderDTO;
import kumarreddyn.github.fda.order.entity.Order;
import kumarreddyn.github.fda.order.util.DateUtil;

@Component
public class OrderConverter {
	
	public Order toOrder(Optional<Order> orderOptional, OrderDTO orderDTO) {
		Order order = new Order();
		if(orderOptional.isPresent()) {
			order = orderOptional.get();
		}
		
		order.setOrderNumber(orderDTO.getOrderNumber());
		order.setTotalPrice(orderDTO.getTotalPrice());
		order.setOrderedBy(orderDTO.getOrderedBy());
		order.setAddressId(orderDTO.getAddressId());
		order.setOrderedDate(DateUtil.toDefaultDateTimeFormat(orderDTO.getOrderedDate()));
		return order;
	}
	
	public OrderDTO toOrderDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setOrderNumber(order.getOrderNumber());
		orderDTO.setTotalPrice(order.getTotalPrice());
		orderDTO.setOrderedBy(order.getOrderedBy());
		orderDTO.setAddressId(order.getAddressId());
		orderDTO.setOrderedDate(DateUtil.toDefaultDateTimeToStringFormat(order.getOrderedDate()));
		return orderDTO;
	}
	
	public Set<OrderDTO> toOrderDTOSet(Set<Order> orders){
		return orders.stream().map(this::toOrderDTO).collect(Collectors.toSet());
	}
	
}
