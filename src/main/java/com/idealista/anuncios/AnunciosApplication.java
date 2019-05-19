package com.idealista.anuncios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealista.anuncios.service.ServiceValidation;

import idealista.anuncios.model.Anuncio;
import idealista.anuncios.model.Foto;

@SpringBootApplication
@ComponentScan(basePackages = "com.idealista.anuncios")
@EnableCaching
public class AnunciosApplication {
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private ServiceValidation serviceValidation;
		
	public static void main(String[] args) {
		SpringApplication.run(AnunciosApplication.class, args);
	}

	@Bean(name = "readAdJsonFile")
	@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public List<Anuncio> readAdJsonFile() throws IOException {

		File photosFile = ResourceUtils.getFile("classpath:anuncios.json");
		List<Anuncio> adsList = mapper.readValue(new String(Files.readAllBytes(photosFile.toPath())), new TypeReference<List<Anuncio>>(){});

		return serviceValidation.calculateScore(adsList);
	}

	@Bean(name = "readPhotosJsonFile")
	@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public List<Foto> readPhotosJsonFile() throws IOException {

		File photosFile = ResourceUtils.getFile("classpath:fotos.json");
		return mapper.readValue(new String(Files.readAllBytes(photosFile.toPath())), new TypeReference<List<Foto>>(){});
	}

}
