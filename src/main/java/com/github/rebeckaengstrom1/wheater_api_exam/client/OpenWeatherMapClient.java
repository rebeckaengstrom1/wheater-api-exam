package com.github.rebeckaengstrom1.wheater_api_exam.client;

import com.github.rebeckaengstrom1.wheater_api_exam.model.CurrentWeatherResponse;
import com.github.rebeckaengstrom1.wheater_api_exam.model.GeoLocation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class OpenWeatherMapClient {
    private final RestClient restClient = RestClient.create();

    public GeoLocation geocode(String city) {

        GeoLocation[] results = restClient.get()
                .uri("https://api.openweathermap.org/geo/1.0/direct", u -> u
                        .queryParam("q", city)
                        .queryParam("limit", 1)
                        .queryParam("appid", "")
                        .build())
                .retrieve()
                .body(GeoLocation[].class);

        return results[0];
    }

    public CurrentWeatherResponse fetchCurrentWeather(double lat, double lon) {

        CurrentWeatherResponse response = restClient.get()
                .uri("https://api.openweathermap.org/data/2.5/weather", u -> u
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("units", "metric")
                    .queryParam("appid", "")
                    .build())
                .retrieve()
                .body(CurrentWeatherResponse.class);

        return response;
    }

}
