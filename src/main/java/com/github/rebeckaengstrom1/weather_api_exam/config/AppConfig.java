package com.github.rebeckaengstrom1.weather_api_exam.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(OpenWeatherMapProperties.class)
public class AppConfig {
    @Bean
    public RestClient restClient(OpenWeatherMapProperties openWeatherMapProperties) {
        return RestClient.builder()
            .requestFactory(new SimpleClientHttpRequestFactory() {{
                setConnectTimeout(Duration.ofMillis(openWeatherMapProperties.connectTimeout()));
                setReadTimeout(Duration.ofMillis(openWeatherMapProperties.readTimeout()));
            }})
            .build();
    }
}
