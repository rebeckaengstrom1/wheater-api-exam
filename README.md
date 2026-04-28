# Weather API Exam

Spring Boot REST API that fetches temperature and perceived temperature from OpenWeatherMap.

## Architecture

```
WeatherController          – HTTP layer, validation, OpenAPI documentation
    └── WeatherService     – Business logic, orchestrates the two API steps
            └── OpenWeatherMapClient   – Communication with OpenWeatherMap (Geocoding + Current Weather)
```

## Requirements

- Java 17
- Maven 3.9+
- API-key from [OpenWeatherMap](https://openweathermap.org/)

## Running Locally

```bash
# Set the API key as an environment variable
export OPENWEATHERMAP_API_KEY=your-key-here

# Build and run
./mvnw spring-boot:run
```

The app starts at http://localhost:8080

### Example

```bash
curl "http://localhost:8080/weather?city=Lund"
```

```json
{
  "temperature": 16.3,
  "feelsLike": "Feels 3°C colder than the actual temperature."
}
```

## Swagger UI

Available at http://localhost:8080/swagger-ui.html when the app is running.
