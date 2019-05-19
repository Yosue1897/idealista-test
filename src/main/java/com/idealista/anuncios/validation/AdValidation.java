package com.idealista.anuncios.validation;

import java.util.Arrays;
import java.util.function.Function;

import idealista.anuncios.model.Anuncio;

public interface AdValidation extends Function<Anuncio, Boolean> {

	static AdValidation descriptionIsNotEmpty() {
		return ad -> !ad.getDescription().isEmpty();
	}

	static AdValidation descriptionIsNotNull() {
		return ad -> ad.getDescription() != null;
	}
	
	static AdValidation descriptionLength(int size) {
		return ad -> ad.getDescription().length() >= size;
	}
	
    default AdValidation and(AdValidation other) {
        return user -> this.apply(user) && other.apply(user);
    }

	static AdValidation descriptionMoreThan(int size) {
		return ad -> Arrays.stream(ad.getDescription().split(" ")).count() >= size;
	}
	
	static AdValidation descriptionLessThan(int size) {
		return ad -> Arrays.stream(ad.getDescription().split(" ")).count() <= size;
	}

	static AdValidation photoIsNotEmpty() {
		return ad -> !ad.getPictures().isEmpty();
	}
	
	static AdValidation stringNotContains(String words) {
		return ad -> ad.getDescription().contains(words);
	}
	
	static AdValidation houseSizeIsNotEmpty() {
		return ad -> ad.getHouseSize() != null && ad.getHouseSize() > 0;
	}
	
	static AdValidation gardenSizeIsNotEmpty() {
		return ad -> ad.getHouseSize() != null && ad.getGardenSize() > 0;
	}
	
	static AdValidation chaletIsComplete() {
		return descriptionIsNotEmpty().and(photoIsNotEmpty()).and(houseSizeIsNotEmpty()).and(gardenSizeIsNotEmpty());
	}
	
	static AdValidation flatIsComplete() {
		return descriptionIsNotEmpty().and(houseSizeIsNotEmpty()).and(photoIsNotEmpty());
	}
}
