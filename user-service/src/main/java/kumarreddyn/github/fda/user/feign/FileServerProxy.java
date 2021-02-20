package kumarreddyn.github.fda.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-server")
public interface FileServerProxy {

	@PostMapping(value = "/upload-file",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	String uploadFile(@RequestParam(name = "filePath" )String filePath, @RequestPart(name = "file") MultipartFile file);
	
}
