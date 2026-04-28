package com.github.rebeckaengstrom1.weather_api_exam.controller;

import com.github.rebeckaengstrom1.weather_api_exam.exception.GlobalExceptionHandler.ErrorResponse;
import com.github.rebeckaengstrom1.weather_api_exam.model.WeatherResponse;
import com.github.rebeckaengstrom1.weather_api_exam.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Validated
@Tag(name = "Weather", description = "Current weather data by city name")
public class WeatherController {
    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    @GetMapping
    @Operation(
            summary = "Get current weather for a city",
            description = "Resolves the city name to coordinates via OpenWeatherMap Geocoding API, " +
                    "then fetches current temperature and perceived temperature."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Weather data returned successfully",
                    content = @Content(schema = @Schema(implementation = WeatherResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing or invalid city parameter",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "Upstream weather API error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<WeatherResponse> getWeather(
            @Parameter(description = "City name to look up", example = "Lund", required = true)
            @RequestParam @NotBlank String city
    ) {
        WeatherResponse response = weatherService.getWeather(city);
        return ResponseEntity.ok(response);
    }
}
