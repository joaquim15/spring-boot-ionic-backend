package br.com.cursoudemy.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.cursoudemy.services.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploafFile(MultipartFile multipartFile) {

		try {
			
			// extrair o nome do arquivo.
			String fileName = multipartFile.getOriginalFilename();
			
			// origem do arquivo
			InputStream is = multipartFile.getInputStream();
			
			// informação do tipo de arquivo enviado
			String contentType = multipartFile.getContentType();

			return uploafFile(is, fileName, contentType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FileException("Erro de IO: " + e.getMessage());
		}

	}

	public URI uploafFile(InputStream stream, String fileName, String contentType) {

		try {
			
			ObjectMetadata meta = new ObjectMetadata();
			
			LOGGER.info("Iniciando upload");
			s3Client.putObject(bucketName, fileName, stream, meta);
			LOGGER.info("Upload finalizado");
			return s3Client.getUrl(bucketName, fileName).toURI();
			
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URL");
		}

	}
}
