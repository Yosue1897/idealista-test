package com.idealista.anuncios.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.idealista.anuncios.config.AnunciosProperties;
import com.idealista.anuncios.service.AdsService;
import com.idealista.anuncios.utils.ReaderFile;

import idealista.anuncios.model.Anuncio;

@Service
@Configuration
@EnableConfigurationProperties(AnunciosProperties.class)
public class AdsServiceImpl implements AdsService {
	
	@Autowired
	private AnunciosProperties anunciosProperties;
	
	public List<Anuncio> adsList = new ArrayList<>();
	
	@Override
	public Anuncio findById() throws IOException {

		ReaderFile.setAnunciosProperties(anunciosProperties);
		return null;
//		return ReaderFile.readAdsJsonFile()
//			.stream()
//			.filter(item -> item.getId().equals(id))
//			.findAny().orElse(null);

	}

	@Override
	public List<Anuncio> calculateScoreAds() throws IOException {
		ReaderFile.setAnunciosProperties(anunciosProperties);
		
		return ReaderFile.calculateScoreList();
	}

	@Override
	public List<Anuncio> userAdsList() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Anuncio> managerAdsList() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Anuncio> irrelevantAds() throws IOException {
		ReaderFile.setAnunciosProperties(anunciosProperties);
		return ReaderFile.calculateScoreList().stream()
				.filter(item -> item.getScore() <=40)
				.sorted(Comparator.comparing(Anuncio::getModifDate))
				.collect(Collectors.toList());
	}
}
