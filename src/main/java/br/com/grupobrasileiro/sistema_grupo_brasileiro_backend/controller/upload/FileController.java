package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.upload.UploadFileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload.FileStorageService;

@Controller
@RequestMapping("/api/v1/files")
public class FileController {
	
	@Autowired
	private FileStorageService fileStorageService;
	
	
	@PostMapping("/upload")
	private UploadFileView uploadFile(@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	          .path("/api/files/download/")
	          .path(fileName)
	          .toUriString();

		return new UploadFileView(fileName, fileDownloadUri, file.getContentType(),file.getSize());
	}
}
