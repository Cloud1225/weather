package com.example.sabiha.weather;

/**
 * Created by Sabiha on 8/29/2017.
 */

public class WeatherDetails {

    private int condition;
    City city;
    System system;
    private long dateTime;
    private int snowVolume;
    private int rainVolume;
    private int cloudiness;
    Wind wind;
    Main main;
    private String base;
    Weather weather;
}

class Coordinator{
    private double longitude;
    private double latitude;
}

class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}

class Main {
    private double temperature;
    private double tempMin;
    private double tempMax;
    private double pressure;
    private double humidity;
    private double seaLevel;
    private double groundLevel;
}

class Wind {
    private double windSpeed;
    private double windDirection;
}

class City {
    private long cityId;
    private String cityName;
    private String countryCode;
    private String countryName;
}

class System {
    private int id;
    private int type;
    private String message;
    private long sunrise;
    private long sunset;
    Coordinator coord;
}