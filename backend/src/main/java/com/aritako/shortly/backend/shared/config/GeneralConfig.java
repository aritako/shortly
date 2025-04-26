package com.aritako.shortly.backend.shared.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}