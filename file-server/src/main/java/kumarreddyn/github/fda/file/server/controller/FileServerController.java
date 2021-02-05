package kumarreddyn.github.fda.file.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import kumarreddyn.github.fda.file.server.service.FileServerService;

@RestController
public class FileServerController {
	
	private final FileServerService fileServerService;
	
	@Autowired
	public FileServerController(final FileServerService fileServerService) {
		this.fileServerService = fileServerService;
	}

	@PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(@RequestParam(name = "filePath" ) String filePath, @RequestParam(name = "file") MultipartFile file) {		
	    return fileServerService.uploadFile(filePath, file);
	}
	
	@GetMapping(value = "/get-file/**")
	public ResponseEntity<Resource> getFile(HttpServletRequest request) {
		String fileLocation = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		fileLocation = fileLocation.replace("/open-api/get-file", "");
		return fileServerService.getFile(fileLocation);
	}

}
