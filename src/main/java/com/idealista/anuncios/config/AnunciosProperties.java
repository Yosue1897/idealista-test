package com.idealista.anuncios.config;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "idealista")
@Getter
@Setter
@NoArgsConstructor
public class AnunciosProperties {
	
	@NotNull
	private Integer descrip;
	
	@NotNull
	private Integer noFoto;
	
	@NotNull
	private Integer fotoHd;
	
	@NotNull
	private Integer fotoNormal;
	
	@NotNull
	private Integer anuncioCompleto;
	
	@NotNull
	private Integer anuncioIrrelevante;
	
	@NotNull
	private Integer puntuacionTotal;
	
	@NotNull
	private Integer chaletWords;
	
	@NotNull
	private Integer flatWords;
	
	@NotNull
	private Integer flatMin;
	
	@NestedConfigurationProperty
	private Descripcion descripcion;
	
	@NestedConfigurationProperty
	private Typology typology;
	
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Descripcion {

    	@NotNull
    	private Integer piso;
    	
    	@NotNull
    	private Integer pisoMas50;
    	
    	@NotNull
    	private Integer chalet;
    	
    	@NotNull
    	private Integer palabras;
    	
    	@NotNull
    	private List<String> words;

    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Typology {
    	
    	@NotNull
    	private String chalet;
    	
    	@NotNull
    	private String flat;
    	
    	@NotNull
    	private String garage;
    	
    }

}
