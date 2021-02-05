package kumarreddyn.github.fda.open.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kumarreddyn.github.fda.open.api.common.ResponseUtil;
import kumarreddyn.github.fda.open.api.constants.RestServiceConstants;

@Service
public class OpenAPIService {

	private final ResponseUtil responseUtil;
	
	@Autowired
	public OpenAPIService(final ResponseUtil responseUtil) {
		this.responseUtil = responseUtil;
	}
	
	public ResponseEntity<Object> checkServicesStatus() {
		Map<String, Object> dataMap = new HashMap<>();
		
		return responseUtil.generateResponse(dataMap, RestServiceConstants.OPEN_API_SERVICE_ACCESSIBLE);
	}

}
