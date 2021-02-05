package kumarreddyn.github.fda.file.server.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServerService {

	@Value("${file.storage.path}")
	private String fileStoragePath;

	public String uploadFile(String filePath, MultipartFile file) {			
		
		File storageDirectory = new File(fileStoragePath + filePath);
	    if (! storageDirectory.exists()){
	    	storageDirectory.mkdirs();		        
	    }
	    
	    String fileName =  fileStoragePath + filePath + file.getOriginalFilename();
		try {
			file.transferTo(new File(fileName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return filePath + file.getOriginalFilename();
	}

	public ResponseEntity<Resource> getFile(String fileLocation) {
		
		String[] fileLocationArray = fileLocation.split("\\/");
		String fileName = fileLocationArray[fileLocationArray.length-1];
		
		File file = new File(fileStoragePath + "/" +fileLocation);
		file.length();
		Path path = Paths.get(file.getAbsolutePath());
		try {
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
			return generateFileResponse(fileName, file.length(), resource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private ResponseEntity<Resource> generateFileResponse(String fileName, long contentLength, ByteArrayResource resource){
		HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(contentLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
	}
}
