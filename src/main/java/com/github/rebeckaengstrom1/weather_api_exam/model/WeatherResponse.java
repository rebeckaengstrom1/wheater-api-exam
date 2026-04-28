package com.github.rebeckaengstrom1.weather_api_exam.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Weather response with temperature and feels-like description")

public record WeatherResponse(
        @Schema(description = "Actual temperature in Celsius", example = "18.3")
        double temperature,
        @Schema(description = "Description of how the temperature feels compared to the actual temperature", example = "Feels 3°C colder than the actual temperature.")
        String feelsLike
) {}
