package com.document.document.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

public class JsonMapper {
    @Bean
    public ObjectMapper getInstance(){
        return new ObjectMapper();
    }
}
