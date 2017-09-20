package com.example.sabiha.weather;

import android.location.Location;
import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 8/31/2017.
 */

public final class JasonParser {
    private static final String OPEN_WEATHER_MAP_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API = "c45b6444e9c0ddd5de1ac925793f78cf";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    static String Tag = JasonParser.class.getSimpleName();

    public static String getWeatherData(Location location) {
        try {
            Log.d(Tag, location.toString());
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, location.getLatitude(), location.getLongitude()));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            connection.disconnect();



            // This value will be 404 if the request was not
            // successful
            //if(data.getInt("cod") != 200){
            //    return null;
            //}
            Log.i(Tag, json.toString());
            return json.toString();
        }catch(Exception e){
            return null;
        }
    }

    public static WeatherDetails getWeatherDetails(String data) throws JSONException {
        try {
            WeatherDetails weather = new WeatherDetails();

            // We start extracting the info

            JSONObject jObject = new JSONObject(data);
            Log.i(Tag, jObject.toString());

            if(!jObject.isNull("coord")) {
                JSONObject coordObj = jObject.getJSONObject("coord");
                weather.system.coord.setLatitude(coordObj.getDouble("lat"));
                weather.system.coord.setLongitude(coordObj.getDouble("lon"));
            }
            Log.i(Tag, String.valueOf(weather.system.coord.getLatitude() + "  " +weather.system.coord.getLongitude()));

            if(!jObject.isNull("weather")) {
                JSONArray jArr = jObject.getJSONArray("weather");

                // We use only the first value
                JSONObject JSONWeather = jArr.getJSONObject(0);
                weather.currentWeather.setId(JSONWeather.getInt("id"));
                weather.currentWeather.setDescription(JSONWeather.getString("description"));
                weather.currentWeather.setMain(JSONWeather.getString("main"));
                weather.currentWeather.setIcon(JSONWeather.getString("icon"));
            }

            weather.base = jObject.getString("base");

            if(!jObject.isNull("main")) {
                JSONObject main = jObject.getJSONObject("main");
                weather.main.setTemperature(main.getDouble("temp"));
                weather.main.setTempMin(main.getDouble("temp_min"));
                weather.main.setTempMax(main.getDouble("temp_max"));
                weather.main.setPressure(main.getDouble("pressure"));
                weather.main.setHumidity(main.getDouble("humidity"));
            }

            if(!jObject.isNull("wind")) {
                JSONObject wind = jObject.getJSONObject("wind");
                weather.wind.setWindDirection(wind.getDouble("deg"));
                weather.wind.setWindSpeed(wind.getDouble("speed"));
            }

            if(!jObject.isNull("clouds")) {
                JSONObject clouds = jObject.getJSONObject("clouds");
                weather.cloudiness = clouds.getDouble("all");
            }

            if(!jObject.isNull("rain")) {
                JSONObject rain = jObject.getJSONObject("rain");
                weather.rainVolume = rain.getDouble("3h");
            }

            if(!jObject.isNull("snow")) {
                JSONObject snow = jObject.getJSONObject("snow");
                weather.snowVolume = snow.getDouble("3h");
            }

            if(!jObject.isNull("sys")) {
                JSONObject sys = jObject.getJSONObject("sys");
                weather.system.setType(sys.getInt("type"));
                weather.system.setId(sys.getInt("id"));
                weather.system.setMessage(sys.getString("message"));
                weather.system.city.setCountryCode(sys.getString("country"));
                weather.system.setSunrise(sys.getLong("sunrise"));
                weather.system.setSunset(sys.getLong("sunset"));
            }

            weather.setDateTime(jObject.getLong("dt"));
            weather.system.city.setCityId(jObject.getLong("id"));
            weather.system.city.setCityName(jObject.getString("name"));
            weather.condition = jObject.getInt("cod");

            Log.i(Tag, weather.system.city.getCityName());

            return weather;
        }
        catch (Exception e)
        {
            Log.d(Tag, e.getMessage());
            return null;
        }
    }

    public static byte[] getWeatherImage(String code) {
        HttpURLConnection connection = null ;
        InputStream is = null;
        try {
            connection = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);
            /*connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();*/

            // Let's read the response
            is = connection.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            is.close();
            connection.disconnect();
            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}
