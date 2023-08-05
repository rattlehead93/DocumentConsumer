package com.document.document.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapper {
    @Bean
    public ObjectMapper getInstance(){
        return new ObjectMapper();
    }
}
