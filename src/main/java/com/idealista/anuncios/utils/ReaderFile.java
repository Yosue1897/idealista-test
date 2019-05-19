package com.idealista.anuncios.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idealista.anuncios.config.AnunciosProperties;
import com.idealista.anuncios.validation.AdValidation;

import idealista.anuncios.model.Anuncio;
import idealista.anuncios.model.Foto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReaderFile {
	
	private static final String HD = "HD";
	private AnunciosProperties anunciosProperties;
		
	ObjectMapper mapper = new ObjectMapper();
	List<Foto> photosList = new ArrayList<>();
	Integer wordScore = 0;

	public List<Anuncio> calculateScoreList() throws IOException {

        return calculateScore(readAdJsonFile());
	}
	
	@Cacheable(value = "adCache")
	public List<Anuncio> readAdJsonFile() throws IOException {
		
		File photosFile = ResourceUtils.getFile("classpath:anuncios.json");
		return mapper.readValue(new String(Files.readAllBytes(photosFile.toPath())), new TypeReference<List<Anuncio>>(){});
	}
	
	@Cacheable(value = "photoCache")
	public List<Foto> readPhotosJsonFile() throws IOException {
		
		File photosFile = ResourceUtils.getFile("classpath:fotos.json");
		photosList = mapper.readValue(new String(Files.readAllBytes(photosFile.toPath())), new TypeReference<List<Foto>>(){});
		return mapper.readValue(new String(Files.readAllBytes(photosFile.toPath())), new TypeReference<List<Foto>>(){});
	}
	
	private List<Anuncio> calculateScore(List<Anuncio> adsList) {
		
		adsList.forEach(item -> {
			if(item.getTypology().equals(anunciosProperties.getTypology().getChalet())) {
				item.setScore(validateChalet(item));
			}else if(item.getTypology().equals(anunciosProperties.getTypology().getFlat())) {
				item.setScore(validateFlat(item));
			}else if(item.getTypology().equals(anunciosProperties.getTypology().getGarage())) {
				item.setScore(validateGarage(item));
			}
		});

		return adsList;
	}
	
	private Integer validateChalet(Anuncio ad) {

		Integer score = calculateWordScoreInDescription(ad);
		score += calculatePhotosScore(ad);
		
		if(AdValidation.descriptionIsNotEmpty().apply(ad)) {
			score += anunciosProperties.getDescrip();
		}
		
		if(AdValidation.descriptionMoreThan(anunciosProperties.getChaletWords()).apply(ad)) {
			score += anunciosProperties.getDescripcion().getPisoMas50();
		}
		
		if(AdValidation.chaletIsComplete().apply(ad)) {
			score += anunciosProperties.getAnuncioCompleto();
		}

		return score;
	}
	
	private Integer validateFlat(Anuncio ad) {
		
		Integer scoreFlat = calculateWordScoreInDescription(ad);
		scoreFlat += calculatePhotosScore(ad);
		
		if(AdValidation.descriptionIsNotEmpty().apply(ad)) {
			scoreFlat += anunciosProperties.getDescrip();
		}
		
		if(AdValidation.descriptionMoreThan(anunciosProperties.getFlatWords()).apply(ad)) {
			scoreFlat += anunciosProperties.getDescripcion().getPisoMas50();
		}else if(AdValidation.descriptionLength(anunciosProperties.getFlatMin()).apply(ad) && AdValidation.descriptionLessThan(anunciosProperties.getFlatWords()-1).apply(ad)) {
			scoreFlat += anunciosProperties.getDescripcion().getPiso();
		}
		
		if(AdValidation.flatIsComplete().apply(ad)) {
			scoreFlat += anunciosProperties.getAnuncioCompleto();
		}
		
		return scoreFlat;
	}
	
	private Integer validateGarage(Anuncio ad) {
		
		Integer garageScore = 0; 
		garageScore += calculatePhotosScore(ad);
		
		if(AdValidation.photoIsNotEmpty().apply(ad)) {
			garageScore += anunciosProperties.getAnuncioCompleto();
		}
		
		return garageScore;
	}
	
	private Integer calculatePhotosScore(Anuncio ad) {
		
		Long photosScore = 0L;
		photosScore += photosList.parallelStream()
				.filter(item -> ad.getPictures().contains(item.getId()))
				.filter(item2 -> item2.getQuality().equals(HD))
				.count() * anunciosProperties.getFotoHd();
		
		photosScore += photosList.parallelStream()
				.filter(item -> ad.getPictures().contains(item.getId()))
				.filter(item2 -> !item2.getQuality().equals(HD))
				.count() * anunciosProperties.getFotoNormal();
	
		if(photosScore.intValue() <=0) {
			photosScore -= anunciosProperties.getNoFoto();
		}

		return photosScore.intValue();
	}
	
	private Integer calculateWordScoreInDescription(Anuncio ad) {
		
		anunciosProperties.getDescripcion().getWords().spliterator().forEachRemaining(item -> 
			wordScore += StringUtils
					.countOccurrencesOf(ad.getDescription(), item) * anunciosProperties.getDescripcion().getPalabras()
		);

		return wordScore;
	}
	
	public static AnunciosProperties getAnunciosProperties() {
		return anunciosProperties;
	}

	public static void setAnunciosProperties(AnunciosProperties anunciosProperties) {
		ReaderFile.anunciosProperties = anunciosProperties;
	}

}
