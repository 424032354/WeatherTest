package com.example.victor.weathertest3.bean;

import java.util.List;

public class WeatherBean {
	
	private String city;
	private String city_id;
	private String release;//发布时间
	private String weather_id;
	private String weather_str;
	private String Max_temp;
	private String Min_temp;
	private String now_temp;  //实时温度
	private String humidity;
	private String wind_direction;
	private String wind_power;
    private String sunrise;
    private String sunset;
    private String sendible_temperature;// 体感温度
	private String uv_index;// 紫外线
	private String carWash_index;// 洗车
	private String dressing_index;// 穿衣
	private String cold_index;// 感冒
	private String comfort_index;// 舒适
	private String exercise_index;// 运动
    private String travel_index;// 旅行
    private String sunscreen_index;// 防晒
    private PMBean pmBean;
	private List<SixDayWeatherBean> SixDayList;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    public String getWeather_str() {
        return weather_str;
    }

    public void setWeather_str(String weather_str) {
        this.weather_str = weather_str;
    }

    public String getMax_temp() {
        return Max_temp;
    }

    public void setMax_temp(String max_temp) {
        Max_temp = max_temp;
    }

    public String getMin_temp() {
        return Min_temp;
    }

    public void setMin_temp(String min_temp) {
        Min_temp = min_temp;
    }

    public String getNow_temp() {
        return now_temp;
    }

    public void setNow_temp(String now_temp) {
        this.now_temp = now_temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_power() {
        return wind_power;
    }

    public void setWind_power(String wind_power) {
        this.wind_power = wind_power;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSendible_temperature() {
        return sendible_temperature;
    }

    public void setSendible_temperature(String sendible_temperature) {
        this.sendible_temperature = sendible_temperature;
    }

    public String getUv_index() {
        return uv_index;
    }

    public void setUv_index(String uv_index) {
        this.uv_index = uv_index;
    }

    public String getCarWash_index() {
        return carWash_index;
    }

    public void setCarWash_index(String carWash_index) {
        this.carWash_index = carWash_index;
    }

    public String getDressing_index() {
        return dressing_index;
    }

    public void setDressing_index(String dressing_index) {
        this.dressing_index = dressing_index;
    }

    public String getCold_index() {
        return cold_index;
    }

    public void setCold_index(String cold_index) {
        this.cold_index = cold_index;
    }

    public String getComfort_index() {
        return comfort_index;
    }

    public void setComfort_index(String comfort_index) {
        this.comfort_index = comfort_index;
    }

    public String getExercise_index() {
        return exercise_index;
    }

    public void setExercise_index(String exercise_index) {
        this.exercise_index = exercise_index;
    }

    public String getTravel_index() {
        return travel_index;
    }

    public void setTravel_index(String travel_index) {
        this.travel_index = travel_index;
    }

    public String getSunscreen_index() {
        return sunscreen_index;
    }

    public void setSunscreen_index(String sunscreen_index) {
        this.sunscreen_index = sunscreen_index;
    }

    public PMBean getPmBean() {
        return pmBean;
    }

    public void setPmBean(PMBean pmBean) {
        this.pmBean = pmBean;
    }

    public List<SixDayWeatherBean> getSixDayList() {
        return SixDayList;
    }

    public void setSixDayList(List<SixDayWeatherBean> sixDayList) {
        SixDayList = sixDayList;
    }
}
