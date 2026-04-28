package com.github.rebeckaengstrom1.weather_api_exam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrentWeatherResponse(
        @JsonProperty("main") MainData main
) {
    public record MainData(
            @JsonProperty("temp") double temp,
            @JsonProperty("feels_like") double feelsLike
    ){}
}
