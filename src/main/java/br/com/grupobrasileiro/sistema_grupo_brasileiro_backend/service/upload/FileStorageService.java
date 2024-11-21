package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.upload;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.FileStorageException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.MyFileNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.SShClientException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import net.schmizz.sshj.sftp.RemoteFile;
import net.schmizz.sshj.sftp.SFTPClient;
import org.apache.commons.lang3.tuple.Pair;
import java.nio.file.Paths;
import java.nio.file.Files;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;


@Service
public class FileStorageService {

    // Configurações do servidor EC2 remoto
    @Value("${sftpHost}")
    private String sftpHost;
    
    @Value("${sftpPort}")
    private int sftpPort;
    
    @Value("${sftpUser}")
    private String sftpUser; 
    
    @Value("${sftpPrivateKey}")
    private String sftpPrivateKey;

    @Value("${sftpRemoteDir}")
    private String sftpRemoteDir;



    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("O nome do arquivo contém uma sequência de caminho inválida " + fileName);
            }

            if (uploadToSFTP(file.getInputStream(), fileName)){
                return fileName;
            };

            return null;
        } catch (Exception e) {
            throw new FileStorageException("Erro ao armazenar o arquivo " + fileName);
        }
    }

    private SSHClient connectToSFTPServer() throws IOException {
        SSHClient sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect(sftpHost, sftpPort);
        String privateKey = System.getProperty("user.dir") + File.separator + sftpPrivateKey;
        sshClient.authPublickey(sftpUser, privateKey);
        return sshClient;
    }

    private boolean uploadToSFTP(InputStream fileInputStream, String remoteFileName) {
        try (SSHClient sshClient = connectToSFTPServer()) {
            try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
                sftpClient.put(new InMemorySourceFile() {
                    @Override
                    public String getName() {
                        return remoteFileName;
                    }
                    @Override
                    public long getLength() {
                        try {
                            return fileInputStream.available();
                        } catch (IOException e) {
                            return 0;
                        }
                    }
                    @Override
                    public InputStream getInputStream() throws IOException {
                        return fileInputStream;
                    }
                }, sftpRemoteDir + "/" + remoteFileName);
                sshClient.disconnect(); 
                return true;
            }
        } catch (IOException e) {
            throw new SShClientException("Erro ao conectar ou fazer upload para o servidor SFTP: {}");
        }
    }

    public Pair<ByteArrayResource, String> loadFileAsResource(String fileName) {
        try (SSHClient sshClient = connectToSFTPServer()) {
            try (SFTPClient sftpClient = sshClient.newSFTPClient();
                RemoteFile remoteFile = sftpClient.open(sftpRemoteDir + "/" + fileName);
                InputStream inputStream = remoteFile.new RemoteFileInputStream(0)) {

                byte[] fileContent = inputStream.readAllBytes();
                String mimeType = Files.probeContentType(Paths.get(fileName));

                return Pair.of(new ByteArrayResource(fileContent), mimeType != null ? mimeType : "application/octet-stream");
            }
        } catch (IOException e) {
            throw new MyFileNotFoundException("Erro ao carregar o arquivo: " + fileName);
        }
    }

}