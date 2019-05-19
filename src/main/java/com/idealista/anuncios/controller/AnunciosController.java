package com.idealista.anuncios.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.anuncios.service.AdsService;

import idealista.anuncios.model.Anuncio;

@RestController
@RequestMapping(value = "/api/anuncios", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnunciosController {

	private AdsService adsService;
	
	@Autowired
	public AnunciosController(AdsService anunciosService) {
		this.adsService = anunciosService;
		
	}
	
	@RequestMapping(value = "/responsable/puntuacion", method = RequestMethod.PUT)
    public ResponseEntity<List<Anuncio>> assignScore() throws IOException {
		return new ResponseEntity<>(adsService.calculateScoreAds(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/responsable/anuncios/irrelevantes", method = RequestMethod.GET)
    public ResponseEntity<List<Anuncio>> irrelevantAds() throws IOException {
		return new ResponseEntity<>(adsService.irrelevantAds(), HttpStatus.OK);
    }
	
}
