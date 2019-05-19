package com.idealista.anuncios.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping(value = "/responsable/anuncio/{id}/puntuacion/{score}")
    public ResponseEntity<Anuncio> assignScore(@PathVariable("id") int id, @PathVariable("score") int score) throws IOException {
		return new ResponseEntity<>(adsService.setScore(id, score), HttpStatus.OK);
    }

	@GetMapping(value = "/responsable/anuncios/irrelevantes")
    public ResponseEntity<List<Anuncio>> irrelevantAds() throws IOException {
		return new ResponseEntity<>(adsService.irrelevantAds(), HttpStatus.OK);
    }

	@GetMapping(value = "/usuario")
    public ResponseEntity<List<Anuncio>> userList() throws IOException {
		return new ResponseEntity<>(adsService.userAdsList(), HttpStatus.OK);
    }

	@GetMapping(value = "/responsable/anuncios")
    public ResponseEntity<List<Anuncio>> findAll() throws IOException {
		return new ResponseEntity<>(adsService.managerAdsList(), HttpStatus.OK);
    }
}
