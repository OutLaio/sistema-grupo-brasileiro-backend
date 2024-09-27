package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.FileStorageException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.upload.FileStorageProperties;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;
	
	  public FileStorageService(FileStorageProperties fileStorageProperties) {
		    this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
		        .toAbsolutePath().normalize();
		    
		    try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception e) {
				throw new FileStorageException("Could not create the directory where the upload files will be stores");
			}
		 }
	  
	  
	  public String storeFile(MultipartFile file) {
		  String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			  if(fileName.contains("..")) {
					throw new FileStorageException("FileName contains invalida path sequence "+ fileName);
			  }
		      Path targetLocation = fileStorageLocation.resolve(fileName);
		      Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
		      

		      return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the upload files will be stores");
		}
	  }
}
