package kumarreddyn.github.fda.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kumarreddyn.github.fda.order.dto.OrderItemDTO;
import kumarreddyn.github.fda.order.service.OrderItemService;

@RestController
@RequestMapping(path = "/order-item")
public class OrderItemController {
	
	private final OrderItemService orderItemService;
	
	public OrderItemController(OrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}

	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody OrderItemDTO orderItemDTO) {
		return orderItemService.save(request, orderItemDTO);
	}

	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody OrderItemDTO orderItemDTO) {
		return orderItemService.update(request, orderItemDTO);
	}

	@PostMapping(path = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody OrderItemDTO orderItemDTO) {
		return orderItemService.delete(request, orderItemDTO);
	}
}
