package com.github.rebeckaengstrom1.weather_api_exam.service;

import com.github.rebeckaengstrom1.weather_api_exam.client.OpenWeatherMapClient;
import com.github.rebeckaengstrom1.weather_api_exam.exception.CityNotFoundException;
import com.github.rebeckaengstrom1.weather_api_exam.model.CurrentWeatherResponse;
import com.github.rebeckaengstrom1.weather_api_exam.model.GeoLocation;
import com.github.rebeckaengstrom1.weather_api_exam.model.WeatherResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("WeatherService")
class WeatherServiceTest {

    @Mock private OpenWeatherMapClient weatherClient;
    @InjectMocks private WeatherService weatherService;

    private static final String CITY      = "Lund";
    private static final GeoLocation LOCATION = new GeoLocation("Lund", 55.7058, 13.1942, "SE");
    private static final double TEMP       = 16.3;
    private static final double FEELS_LIKE = 13.0;

    private void mockWeatherClientHappyResponse() {
        when(weatherClient.geocode(CITY)).thenReturn(LOCATION);
        when(weatherClient.fetchCurrentWeather(LOCATION.lat(), LOCATION.lon()))
                .thenReturn(new CurrentWeatherResponse(
                        new CurrentWeatherResponse.MainData(TEMP, FEELS_LIKE)));
    }

    @Test
    @DisplayName("returns correct temperature and description for a known city")
    void getWeather_returnsCorrectResponse() {
        mockWeatherClientHappyResponse();
        WeatherResponse response = weatherService.getWeather(CITY);
        assertThat(response.temperature()).isEqualTo(TEMP);
        assertThat(response.feelsLike()).isEqualTo("Feels 3°C colder than the actual temperature.");
    }

    @Test
    @DisplayName("delegates geocoding to client with the supplied city name")
    void getWeather_callsGeocodeWithCity() {
        mockWeatherClientHappyResponse();
        weatherService.getWeather(CITY);
        verify(weatherClient).geocode(CITY);
    }

    @Test
    @DisplayName("delegates weather fetch using coordinates from geocode result")
    void getWeather_fetchesWeatherWithGeocodeCoordinates() {
        mockWeatherClientHappyResponse();
        weatherService.getWeather(CITY);
        verify(weatherClient).fetchCurrentWeather(LOCATION.lat(), LOCATION.lon());
    }

    @Test
    @DisplayName("propagates CityNotFoundException when city is not found")
    void getWeather_propagatesCityNotFoundException() {
        when(weatherClient.geocode("UnknownCity")).thenThrow(new CityNotFoundException("UnknownCity"));
        assertThatThrownBy(() -> weatherService.getWeather("UnknownCity"))
                .isInstanceOf(CityNotFoundException.class)
                .hasMessageContaining("UnknownCity");
    }
}