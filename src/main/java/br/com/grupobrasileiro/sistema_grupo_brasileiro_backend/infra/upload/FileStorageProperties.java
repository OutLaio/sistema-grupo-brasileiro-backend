package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.upload;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="file")
@Data
public class FileStorageProperties {
	private String uploadDir;
}