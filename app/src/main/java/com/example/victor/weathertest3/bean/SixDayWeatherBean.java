package com.example.victor.weathertest3.bean;

/**
 * Created by victor on 2016/8/21.
 */
public class SixDayWeatherBean {

    private String date;
    private String Weather_id;
    private String MaxTemp;
    private String MinTemp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather_id() {
        return Weather_id;
    }

    public void setWeather_id(String weather_id) {
        Weather_id = weather_id;
    }

    public String getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        MaxTemp = maxTemp;
    }

    public String getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(String minTemp) {
        MinTemp = minTemp;
    }
}
