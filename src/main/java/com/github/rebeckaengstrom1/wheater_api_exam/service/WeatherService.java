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

    /**
     * Returns weather data for the given city.
     *
     * @param city the city name, e.g. "Lund"
     * @return a {@link WeatherResponse} with temperature and feels-like description
     */
    public WeatherResponse getWeather(String city) {

        GeoLocation location = weatherClient.geocode(city);

        CurrentWeatherResponse weatherResponse = weatherClient.fetchCurrentWeather(location.lat(), location.lon());

        double temp      = weatherResponse.main().temp();
        double feelsLike = weatherResponse.main().feelsLike();

        String description = getFeelsLikeDesc(temp, feelsLike);

        return new WeatherResponse(temp, description);
    }

    private String getFeelsLikeDesc(double temp, double feelsLike){
        double diff = Math.round(feelsLike - temp);
        long absDiff = Math.abs(Math.round(diff));
        String description;

        if (diff == 0) {
            description = "The temperature feels approximately the same as the actual temperature.";
        }
        else if (diff < 0) {
            description = String.format("Feels %d°C colder than the actual temperature.", absDiff);
        } else {
            description = String.format("Feels %d°C warmer than the actual temperature.", absDiff);
        }

        return description;
    }
}
