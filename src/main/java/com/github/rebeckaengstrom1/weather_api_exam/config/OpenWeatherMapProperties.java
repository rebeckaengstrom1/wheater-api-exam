package com.github.rebeckaengstrom1.weather_api_exam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openweathermap")

public record OpenWeatherMapProperties(
        String apiKey,
        String baseUrl,
        String units
) {
}
