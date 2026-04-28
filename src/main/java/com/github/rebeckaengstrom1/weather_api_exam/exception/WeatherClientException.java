package com.github.rebeckaengstrom1.weather_api_exam.exception;

/**
 * Thrown when an upstream call to OpenWeatherMap fails unexpectedly.
 */
public class WeatherClientException extends RuntimeException {
    public WeatherClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherClientException(String message) {
        super(message);
    }
}
