package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.FileStorageException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.MyFileNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.upload.FileStorageProperties;
import org.springframework.core.io.Resource;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;
	
	  public FileStorageService(FileStorageProperties fileStorageProperties) {
			this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
				.toAbsolutePath().normalize();
			
			try {
				Files.createDirectories(this.fileStorageLocation);
			} catch (Exception e) {
				throw new FileStorageException("Não foi possível criar o diretório onde os arquivos de upload serão armazenados");
			}
		 }
	  
	  
	  public String storeFile(MultipartFile file) {
		  String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			  if(fileName.contains("..")) {
					throw new FileStorageException("FileName contém sequência de caminho inválida "+ fileName);
			  }
			  Path targetLocation = fileStorageLocation.resolve(fileName);
			  Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
			  

			  return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Não foi possível criar o diretório onde os arquivos de upload serão armazenados");
		}
	  }

	  public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("Arquivo não encontrado " + fileName);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("Arquivo não encontrado " + fileName);
		}
		
	}
}
