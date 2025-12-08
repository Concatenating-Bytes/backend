package com.example.backend.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //python microservice implementation
    @Bean
    public WebClient pythonFaceWebClient(@Value("${python.service.url:http://localhost:9999}") String url){
        return WebClient.builder().baseUrl(url).build();
    }
}
