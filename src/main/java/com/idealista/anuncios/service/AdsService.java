package com.idealista.anuncios.service;

import java.io.IOException;
import java.util.List;

import idealista.anuncios.model.Anuncio;

public interface AdsService {
	
	List<Anuncio> userAdsList() throws IOException;
	
	List<Anuncio> managerAdsList() throws IOException;
	
	List<Anuncio> irrelevantAds() throws IOException;
	
	Anuncio setScore(int id, int score);
}
