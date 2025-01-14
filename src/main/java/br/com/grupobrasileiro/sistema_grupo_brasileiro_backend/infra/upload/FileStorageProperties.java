package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix="file")
public class FileStorageProperties {
	private String uploadDir;
	
	  public String getUploadDir() {
		  return uploadDir;
	  }

	  public void setUploadDir(String uploadDir) {
	    this.uploadDir = uploadDir;
	  }
}
