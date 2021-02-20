package kumarreddyn.github.fda.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kumarreddyn.github.fda.order.constants.DataConstants;
import kumarreddyn.github.fda.order.constants.RestServiceConstants;
import kumarreddyn.github.fda.order.dto.OrderDTO;
import kumarreddyn.github.fda.order.dto.converter.OrderConverter;
import kumarreddyn.github.fda.order.entity.Order;
import kumarreddyn.github.fda.order.repository.OrderRepository;
import kumarreddyn.github.fda.order.util.ResponseUtil;
import kumarreddyn.github.fda.order.util.SecurityUtil;

@Service
public class OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderService.class);
	private final OrderRepository orderRepository;
	private final OrderConverter orderConverter;
	private final SecurityUtil securityUtil;
	private final ResponseUtil responseUtil;
	
	public OrderService(OrderRepository orderRepository,
			OrderConverter orderConverter,
			SecurityUtil securityUtil, ResponseUtil responseUtil) {
		this.orderRepository = orderRepository;
		this.orderConverter = orderConverter;
		this.securityUtil = securityUtil;
		this.responseUtil = responseUtil;
	}

	public Optional<Order> findOrderById(Long orderId){
		return orderRepository.findById(orderId);
	}
	
	public Order toOrder(Optional<Order> orderOptional, OrderDTO orderDTO) {
		return orderConverter.toOrder(orderOptional, orderDTO);
	}
	
	public OrderDTO toOrderDTO(Order order) {
		return orderConverter.toOrderDTO(order);
	}
	
	public Set<OrderDTO> toOrderDTOSet(Set<Order> orders){
		return orderConverter.toOrderDTOSet(orders);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, OrderDTO orderDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Order> orderOptional = Optional.empty();
			Order order = toOrder(orderOptional, orderDTO);
			order.setActive(true);
			order = save(request, order);
			dataMap.put(DataConstants.ORDER, toOrderDTO(order));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_SAVED);
		}catch (Exception e) {
			logger.error("Not able to save order. {} - {}", orderDTO, e.getMessage());
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_NOT_SAVED);
		}
	}
	
	public Order save(HttpServletRequest request, Order order) {
		order.setCreatedDate(new Date());
		order.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return orderRepository.save(order);
	}

	public ResponseEntity<Object> update(HttpServletRequest request, OrderDTO orderDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Order> orderOptional = findOrderById(orderDTO.getOrderId());
			if(orderOptional.isPresent()) {
				Order order = toOrder(orderOptional, orderDTO);
				order = update(request, order);
				dataMap.put(DataConstants.ORDER, toOrderDTO(order));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_NOT_FOUND);
			}
		}catch (Exception e) {
			logger.error("Not able to update order. {} - {}", orderDTO, e.getMessage());
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_NOT_UPDATED);
		}
	}
	
	public Order update(HttpServletRequest request, Order order) {
		order.setUpdatedDate(new Date());
		order.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return orderRepository.save(order);
	}


	public ResponseEntity<Object> delete(HttpServletRequest request, OrderDTO orderDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Order> orderOptional = findOrderById(orderDTO.getOrderId());
			if(orderOptional.isPresent()) {
				Order order = orderOptional.get();
				order.setActive(false);
				update(request, order);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_NOT_FOUND);
			}
		}catch (Exception e) {
			logger.error("Not able to delete order. {} - {}", orderDTO, e.getMessage());
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_NOT_DELETED);
		}
	}
	
}
