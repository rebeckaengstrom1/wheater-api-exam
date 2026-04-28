package com.github.rebeckaengstrom1.wheater_api_exam.controller;

import com.github.rebeckaengstrom1.wheater_api_exam.model.WeatherResponse;
import com.github.rebeckaengstrom1.wheater_api_exam.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping

    public ResponseEntity<WeatherResponse> getWeather(
            @RequestParam @NotBlank String city
    ) {
        WeatherResponse response = weatherService.getWeather(city);
        return ResponseEntity.ok(response);
    }
}
