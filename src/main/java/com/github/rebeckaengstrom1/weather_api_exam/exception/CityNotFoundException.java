package com.github.rebeckaengstrom1.weather_api_exam.exception;
/**
 * Thrown when a city cannot be found via the Geocoding API.
 */
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String city) {
        super("City not found: " + city);
    }
}
