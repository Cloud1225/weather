package com.example.sabiha.weather;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sabiha on 8/29/2017.
 */

public class WeatherDetails {

    public int condition;
    private long dateTime;
    public double snowVolume;
    public double rainVolume;
    public double cloudiness;
    public String base;
    public byte[] iconData;
    public System system = new System();
    public Wind wind = new Wind();
    public Main main = new Main();
    public Weather currentWeather = new Weather();

    public Date getDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTime * 1000L);
        return cal.getTime();
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public class Coordinator{
        private double longitude;
        private double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    public class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Main {
        private double temperature;
        private double tempMin;
        private double tempMax;
        private double pressure;
        private double humidity;
        private double seaLevel;
        private double groundLevel;

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(double seaLevel) {
            this.seaLevel = seaLevel;
        }

        public double getGroundLevel() {
            return groundLevel;
        }

        public void setGroundLevel(double groundLevel) {
            this.groundLevel = groundLevel;
        }
    }

    public class Wind {
        private double windSpeed;
        private double windDirection;

        public double getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }

        public double getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(double windDirection) {
            this.windDirection = windDirection;
        }
    }

    public class City {
        private long cityId;
        private String cityName;
        private String countryCode;
        private String countryName;

        public long getCityId() {
            return cityId;
        }

        public void setCityId(long cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }
    }

    public class System {
        private int id;
        private int type;
        private String message;
        private long sunrise;
        private long sunset;
        public Coordinator coord = new Coordinator();
        public City city = new City();

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public double getSunriseHours() {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sunrise * 1000L);
            double hours = cal.getTime().getHours() + (cal.getTime().getMinutes()/60.0) + (cal.getTime().getSeconds()/3600.0);
            return hours;
        }
        public Date getSunrise()
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sunrise * 1000L);
            return cal.getTime();
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public double getSunsetHours() {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sunset * 1000L);
            double hours = cal.getTime().getHours() + (cal.getTime().getMinutes()/60.0) + (cal.getTime().getSeconds()/3600.0);
            return hours;
        }
        public Date getSunset()
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(sunset * 1000L);
            return cal.getTime();
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }
    }
}
