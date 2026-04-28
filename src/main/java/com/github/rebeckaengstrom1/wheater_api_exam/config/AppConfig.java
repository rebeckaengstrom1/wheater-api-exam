package com.github.rebeckaengstrom1.wheater_api_exam.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(OpenWeatherMapProperties.class)
public class AppConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
