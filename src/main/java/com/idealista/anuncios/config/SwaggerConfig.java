package com.idealista.anuncios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
		@Bean
	public Docket produceApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiEndPointsInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.idealista.anuncios.controller")).paths(paths()).build();
	}
    
    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("ANUNCIOS REST API")
            .description("Listado de anuncios REST API")
            .license("Apache 2.0")
            .version("1.0.0")
            .build();
    }

	private Predicate<String> paths() {
        return Predicates.and(
        PathSelectors.regex("/api/anuncios.*"),
        Predicates.not(PathSelectors.regex("/error.*")));
        }
}
