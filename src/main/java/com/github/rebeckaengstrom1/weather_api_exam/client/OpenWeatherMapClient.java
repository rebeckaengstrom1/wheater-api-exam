package com.github.rebeckaengstrom1.weather_api_exam.client;

import com.github.rebeckaengstrom1.weather_api_exam.config.OpenWeatherMapProperties;
import com.github.rebeckaengstrom1.weather_api_exam.exception.CityNotFoundException;
import com.github.rebeckaengstrom1.weather_api_exam.exception.WeatherClientException;
import com.github.rebeckaengstrom1.weather_api_exam.model.CurrentWeatherResponse;
import com.github.rebeckaengstrom1.weather_api_exam.model.GeoLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenWeatherMapClient {
    private static final Logger log = LoggerFactory.getLogger(OpenWeatherMapClient.class);
    private final RestClient restClient;
    private final OpenWeatherMapProperties openWeatherMapProperties;
    private static final String GEO_PATH = "/geo/1.0/direct";
    private static final String WEATHER_PATH = "/data/2.5/weather";

    public OpenWeatherMapClient(RestClient restClient, OpenWeatherMapProperties openWeatherMapProperties){
        this.restClient = restClient;
        this.openWeatherMapProperties = openWeatherMapProperties;
    }
    /**
     * Resolves a city name to its geographic coordinates.
     *
     * @param city the city name to look up
     * @return a {@link GeoLocation} with lat/lon
     * @throws CityNotFoundException  if no results are returned
     * @throws WeatherClientException if the HTTP call fails
     */
    public GeoLocation geocode(String city) {
        String url = UriComponentsBuilder
                .fromUriString(openWeatherMapProperties.baseUrl() + GEO_PATH)
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", openWeatherMapProperties.apiKey())
                .toUriString();
        try {
            GeoLocation[] results = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(GeoLocation[].class);
            if(results.length == 0){
                throw new CityNotFoundException(city);
            }
            GeoLocation location = results[0];
            log.debug("Resolved '{}' → lat={}, lon={}", city, location.lat(), location.lon());
            return location;
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new WeatherClientException("Invalid OpenWeatherMap API key.", e);
        } catch (RestClientException e) {
            throw new WeatherClientException("Failed to geocode city: " + city, e);
        }
    }

    /**
     * Fetches current weather data for the given coordinates.
     *
     * @param lat latitude
     * @param lon longitude
     * @return the raw API response containing temperature data
     * @throws WeatherClientException if the HTTP call fails
     */
    public CurrentWeatherResponse fetchCurrentWeather(double lat, double lon) {

        String url = UriComponentsBuilder
                .fromUriString(openWeatherMapProperties.baseUrl() + WEATHER_PATH)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("units", openWeatherMapProperties.units())
                .queryParam("appid", openWeatherMapProperties.apiKey())
                .toUriString();

        log.debug("Fetching weather for lat={}, lon={}", lat, lon);

        try {
            CurrentWeatherResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(CurrentWeatherResponse.class);
            if (response == null || response.main() == null) {
                throw new WeatherClientException("Empty response from weather API.");
            }
            return response;
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new WeatherClientException("Invalid OpenWeatherMap API key.", e);
        } catch (RestClientException e) {
            throw new WeatherClientException("Failed to fetch weather data.", e);
        }
    }

}
