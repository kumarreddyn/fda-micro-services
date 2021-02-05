package kumarreddyn.github.fda.open.api.common;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ResponseUtil {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ObjectMapper objectMapper;
	
	public  ResponseEntity<Object> generateResponse(Map<String, Object> dataMap, String responseCode){
		
		RestResponse restResponse = new RestResponse(responseCode, dataMap);
		restResponse.setCode(responseCode);
		restResponse.setData(dataMap);
		
		String jsonResponse = "{}";
		
		try {
			jsonResponse = objectMapper.writeValueAsString(restResponse);
		} catch (JsonProcessingException e) {			
			logger.error("Not able to serialize java value to a String. value: {} - {}", restResponse, e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);				
	}
	
	
}

class RestResponse {

	private String code;	
	private Map<String, Object> data;

	public RestResponse(String code, Map<String, Object> data) {
		this.code = code;		
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "UserServiceAPIResponse [code=" + code + ", data=" + data + "]";
	}

}
