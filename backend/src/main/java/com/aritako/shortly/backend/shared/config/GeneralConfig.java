package com.aritako.shortly.backend.shared.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GeneralConfig{
  @Bean
  public Module hibernateModule() {
      return new Hibernate5JakartaModule();
  }
  @Bean
  public ModelMapper modelMapper() {
      return new ModelMapper();
  }
  @Bean
  public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
          @Override
          public void addCorsMappings(CorsRegistry registry) {
              registry.addMapping("/**")
                  .allowedOrigins("http://localhost:3000")
                  .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                  .allowedHeaders("*")
                  .allowCredentials(true);
          }
      };
  }
}