package com.idealista.anuncios.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.idealista.anuncios.config.AnunciosProperties;
import com.idealista.anuncios.validation.AdValidation;

import idealista.anuncios.model.Anuncio;
import idealista.anuncios.model.Foto;

@Component
@Configuration
@EnableConfigurationProperties(AnunciosProperties.class)
public class ServiceValidation {
	
	private static final String HD = "HD";
	private static final String SD = "SD";
	Integer wordScore = 0;
	
	@Autowired
	private AnunciosProperties anunciosProperties;
	
	@Resource(name = "readPhotosJsonFile")
	List<Foto> readPhotosJsonFile;
	
	public List<Anuncio> calculateScore(List<Anuncio> adsList) {
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

		Integer garageScore = calculateWordScoreInDescription(ad);
		garageScore += calculatePhotosScore(ad);
		
		if(AdValidation.descriptionIsNotEmpty().apply(ad)) {
			garageScore += anunciosProperties.getDescrip();
		}

		if(AdValidation.garageIsComplete().apply(ad)) {
			garageScore += anunciosProperties.getAnuncioCompleto();
		}
		
		System.out.println("garage total " + ad.getId() + " " + garageScore);
		return garageScore;
	}
	
	private Integer calculatePhotosScore(Anuncio ad) {
		
		Long photosScore = 0L;
		photosScore += readPhotosJsonFile.parallelStream()
				.filter(item -> ad.getPictures().contains(item.getId()))
				.filter(item2 -> item2.getQuality().equals(HD))
				.count() * anunciosProperties.getFotoHd();

		photosScore += readPhotosJsonFile
				.parallelStream()
				.filter(item -> ad.getPictures().contains(item.getId()))
				.filter(item2 -> item2.getQuality().equals(SD))
				.count() * anunciosProperties.getFotoNormal();
	
		if(photosScore.intValue() <=0) {
			photosScore -= anunciosProperties.getNoFoto();
		}

		return photosScore.intValue();
	}
	
	private Integer calculateWordScoreInDescription(Anuncio ad) {
		wordScore = 0;
		anunciosProperties.getDescripcion().getWords().spliterator()
			.forEachRemaining(item -> 
				wordScore += StringUtils
					.countOccurrencesOf(ad.getDescription().toLowerCase(), item.toLowerCase()) * anunciosProperties.getDescripcion().getPalabras()
		);

		return wordScore;
	}
}
