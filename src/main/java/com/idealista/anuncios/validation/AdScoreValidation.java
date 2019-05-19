package com.idealista.anuncios.validation;

import java.util.function.Function;

import idealista.anuncios.model.Anuncio;

public interface AdScoreValidation extends Function<Anuncio, Integer>  {

}
