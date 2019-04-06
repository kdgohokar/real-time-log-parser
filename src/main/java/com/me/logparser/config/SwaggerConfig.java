package com.me.logparser.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2)          
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.me.logparser"))
				.paths(PathSelectors.ant("/parser/*"))
				.build()
				.apiInfo(apiInfo());                                        
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Custom Log Parser API", 
				"Counting the HTTP status code from the webserver access log continusly", 
				"API TOS", 
				"Terms of service", 
				new Contact("Kiran Gohokar", "https://github.com/kdgohokar", "kdgohokar@gmail.com"), 
				null, null, Collections.emptyList());
	}
}
