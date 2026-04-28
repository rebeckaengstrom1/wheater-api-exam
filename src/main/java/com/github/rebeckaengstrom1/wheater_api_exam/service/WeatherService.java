package com.github.rebeckaengstrom1.wheater_api_exam.service;

import com.github.rebeckaengstrom1.wheater_api_exam.client.OpenWeatherMapClient;
import com.github.rebeckaengstrom1.wheater_api_exam.model.CurrentWeatherResponse;
import com.github.rebeckaengstrom1.wheater_api_exam.model.GeoLocation;
import com.github.rebeckaengstrom1.wheater_api_exam.model.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final OpenWeatherMapClient weatherClient;
    public WeatherService(OpenWeatherMapClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherResponse getWeather(String city) {

        GeoLocation location = weatherClient.geocode(city);

        CurrentWeatherResponse weatherResponse = weatherClient.fetchCurrentWeather(location.lat(), location.lon());

        double temp      = weatherResponse.main().temp();
        double feelsLike = weatherResponse.main().feelsLike();

        String description = "Feels like " + feelsLike + " °C";

        return new WeatherResponse(temp, description);
    }
}
