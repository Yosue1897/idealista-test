package com.idealista.anuncios;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import com.idealista.anuncios.utils.ReaderFile;

@SpringBootApplication
@ComponentScan(basePackages = "com.idealista.anuncios")
@EnableCaching
public class AnunciosApplication implements ApplicationRunner{
		
	public static void main(String[] args) {
		SpringApplication.run(AnunciosApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ReaderFile.readPhotosJsonFile();
	}

}
