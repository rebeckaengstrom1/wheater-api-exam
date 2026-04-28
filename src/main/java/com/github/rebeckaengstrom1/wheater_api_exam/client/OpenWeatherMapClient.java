package com.github.rebeckaengstrom1.wheater_api_exam.client;

import com.github.rebeckaengstrom1.wheater_api_exam.config.OpenWeatherMapProperties;
import com.github.rebeckaengstrom1.wheater_api_exam.model.CurrentWeatherResponse;
import com.github.rebeckaengstrom1.wheater_api_exam.model.GeoLocation;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenWeatherMapClient {
    private final RestClient restClient;
    private final OpenWeatherMapProperties openWeatherMapProperties;
    private static final String GEO_PATH = "/geo/1.0/direct";
    private static final String WEATHER_PATH = "/data/2.5/weather";

    public OpenWeatherMapClient(RestClient restClient, OpenWeatherMapProperties openWeatherMapProperties){
        this.restClient = restClient;
        this.openWeatherMapProperties = openWeatherMapProperties;
    }
    public GeoLocation geocode(String city) {
        String url = UriComponentsBuilder
                .fromUriString(openWeatherMapProperties.baseUrl() + GEO_PATH)
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", openWeatherMapProperties.apiKey())
                .toUriString();
        GeoLocation[] results = restClient.get()
                .uri(url)
                .retrieve()
                .body(GeoLocation[].class);

        return results[0];
    }

    public CurrentWeatherResponse fetchCurrentWeather(double lat, double lon) {

        String url = UriComponentsBuilder
                .fromUriString(openWeatherMapProperties.baseUrl() + WEATHER_PATH)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("units", openWeatherMapProperties.units())
                .queryParam("appid", openWeatherMapProperties.apiKey())
                .toUriString();

        CurrentWeatherResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(CurrentWeatherResponse.class);

        return response;
    }

}
