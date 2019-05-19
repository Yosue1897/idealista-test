package com.idealista.anuncios.service;

import java.io.IOException;
import java.util.List;

import idealista.anuncios.model.Anuncio;

public interface AdsService {

	Anuncio findById() throws IOException;
		
	List<Anuncio> calculateScoreAds() throws IOException;
	
	List<Anuncio> userAdsList() throws IOException; //tienen que ir filtrados para que no muestre anuncios irrelevantes.
	
	List<Anuncio> managerAdsList() throws IOException;
	
	List<Anuncio> irrelevantAds() throws IOException;
}
