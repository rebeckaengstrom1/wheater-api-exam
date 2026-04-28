package com.github.rebeckaengstrom1.wheater_api_exam.model;

public record GeoLocation(
        String name,
        double lat,
        double lon,
        String country
) {}
