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
import kumarreddyn.github.fda.order.dto.OrderItemDTO;
import kumarreddyn.github.fda.order.dto.converter.OrderItemConverter;
import kumarreddyn.github.fda.order.entity.OrderItem;
import kumarreddyn.github.fda.order.repository.OrderItemRepository;
import kumarreddyn.github.fda.order.util.ResponseUtil;
import kumarreddyn.github.fda.order.util.SecurityUtil;

@Service
public class OrderItemService {

	private final Logger logger = LoggerFactory.getLogger(OrderItemService.class);
	private final OrderItemRepository orderItemRepository;
	private final OrderItemConverter orderItemConverter;
	private final SecurityUtil securityUtil;
	private final ResponseUtil responseUtil;
	
	public OrderItemService(OrderItemRepository orderItemRepository,
			OrderItemConverter orderItemConverter,
			SecurityUtil securityUtil, ResponseUtil responseUtil) {
		this.orderItemRepository = orderItemRepository;
		this.orderItemConverter = orderItemConverter;
		this.securityUtil = securityUtil;
		this.responseUtil = responseUtil;
	}

	public Optional<OrderItem> findOrderItemById(Long orderItemId){
		return orderItemRepository.findById(orderItemId);
	}
	
	public OrderItem toOrder(Optional<OrderItem> orderItemOptional, OrderItemDTO orderItemDTO) {
		return orderItemConverter.toOrderItem(orderItemOptional, orderItemDTO);
	}
	
	public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
		return orderItemConverter.toOrderItemDTO(orderItem);
	}
	
	public Set<OrderItemDTO> toOrderItemDTOSet(Set<OrderItem> orderItems){
		return orderItemConverter.toOrderItemDTOSet(orderItems);
	}
	
	public ResponseEntity<Object> save(HttpServletRequest request, OrderItemDTO orderItemDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<OrderItem> orderItemOptional = Optional.empty();
			OrderItem orderItem = toOrder(orderItemOptional, orderItemDTO);
			orderItem.setActive(true);
			orderItem = save(request, orderItem);
			dataMap.put(DataConstants.ORDER_ITEM, toOrderItemDTO(orderItem));
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_SAVED);
		}catch (Exception e) {
			logger.error("Not able to save order item. {} - {}", orderItemDTO, e.getMessage());
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_NOT_SAVED);
		}
	}
	
	public OrderItem save(HttpServletRequest request, OrderItem orderItem) {
		orderItem.setCreatedDate(new Date());
		orderItem.setCreatedBy(securityUtil.getLoggedInMemberId(request));
		return orderItemRepository.save(orderItem);
	}

	public ResponseEntity<Object> update(HttpServletRequest request, OrderItemDTO orderItemDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<OrderItem> orderItemOptional = findOrderItemById(orderItemDTO.getOrderItemId());
			if(orderItemOptional.isPresent()) {
				OrderItem order = toOrder(orderItemOptional, orderItemDTO);
				order = update(request, order);
				dataMap.put(DataConstants.ORDER_ITEM, toOrderItemDTO(order));
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_UPDATED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_NOT_FOUND);
			}
		}catch (Exception e) {
			logger.error("Not able to update order item. {} - {}", orderItemDTO, e.getMessage());
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_NOT_UPDATED);
		}
	}
	
	public OrderItem update(HttpServletRequest request, OrderItem orderItem) {
		orderItem.setUpdatedDate(new Date());
		orderItem.setUpdatedBy(securityUtil.getLoggedInMemberId(request));
		return orderItemRepository.save(orderItem);
	}


	public ResponseEntity<Object> delete(HttpServletRequest request, OrderItemDTO orderItemDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<OrderItem> orderItemOptional = findOrderItemById(orderItemDTO.getOrderItemId());
			if(orderItemOptional.isPresent()) {
				OrderItem order = orderItemOptional.get();
				order.setActive(false);
				update(request, order);
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_DELETED);
			}else {
				return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_NOT_FOUND);
			}
		}catch (Exception e) {
			logger.error("Not able to delete order item. {} - {}", orderItemDTO, e.getMessage());
			return responseUtil.generateResponse(dataMap, RestServiceConstants.ORDER_ITEM_NOT_DELETED);
		}
	}
	
}
