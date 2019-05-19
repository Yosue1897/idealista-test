package com.idealista.anuncios.service.impl;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.idealista.anuncios.config.AnunciosProperties;
import com.idealista.anuncios.service.AdsService;

import idealista.anuncios.model.Anuncio;

@Service
@Configuration
@EnableConfigurationProperties(AnunciosProperties.class)
public class AdsServiceImpl implements AdsService {

	@Resource(name = "readAdJsonFile")
    List<Anuncio> readAdJsonFile;
	
	@Autowired
	private AnunciosProperties anunciosProperties;

	@Override
	public List<Anuncio> userAdsList() throws IOException {
		return readAdJsonFile.stream()
				.filter(item -> item.getScore() > anunciosProperties.getAnuncioIrrelevante())
				.sorted(Comparator.comparing(Anuncio::getScore).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public List<Anuncio> managerAdsList() throws IOException {
		return readAdJsonFile;
	}

	@Override
	public List<Anuncio> irrelevantAds() throws IOException {

		return readAdJsonFile.stream()
		.filter(item -> item.getScore() <= anunciosProperties.getAnuncioIrrelevante())
		.sorted(Comparator.comparing(Anuncio::getModifDate))
		.collect(Collectors.toList());
	}

	@Override
	public Anuncio setScore(int id, int score) {
		
		Optional<Anuncio> optionalAd = Optional.ofNullable(readAdJsonFile
			.stream()
			.filter(item -> item.getId().equals(id))
			.findAny()).orElse(null);

		optionalAd.ifPresent(item -> item.setScore(score));
		return optionalAd.get();
		
	}
}
