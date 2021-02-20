package kumarreddyn.github.fda.open.api.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kumarreddyn.github.fda.open.api.common.ResponseUtil;
import kumarreddyn.github.fda.open.api.constants.RestServiceConstants;
import kumarreddyn.github.fda.open.api.feign.CatalogueServiceProxy;
import kumarreddyn.github.fda.open.api.feign.UserServiceProxy;

@Service
public class OpenAPIService {

	private final ResponseUtil responseUtil;
	private final UserServiceProxy userServiceProxy;
	private final CatalogueServiceProxy catalogueServiceProxy;
	
	public OpenAPIService(final ResponseUtil responseUtil,
			UserServiceProxy userServiceProxy, CatalogueServiceProxy catalogueServiceProxy) {
		this.responseUtil = responseUtil;
		this.userServiceProxy = userServiceProxy;
		this.catalogueServiceProxy = catalogueServiceProxy;
	}
	
	public ResponseEntity<Object> checkServicesStatus() {
		Map<String, Object> dataMap = new HashMap<>();
		return responseUtil.generateResponse(dataMap, RestServiceConstants.OPEN_API_SERVICE_ACCESSIBLE);
	}

	public ResponseEntity<Object> register(HttpServletRequest request, String user) {
		return userServiceProxy.register(user);
	}

	public ResponseEntity<Object> login(HttpServletRequest request, String login) {
		return userServiceProxy.login(login);
	}

	public ResponseEntity<Object> getAllFoodOutlets() {
		return catalogueServiceProxy.getAllFoodOutlets();
	}

	public ResponseEntity<Object> getAllFoodItems(Long foodOutletId) {
		return catalogueServiceProxy.getAllFoodItems(foodOutletId);
	}

}
