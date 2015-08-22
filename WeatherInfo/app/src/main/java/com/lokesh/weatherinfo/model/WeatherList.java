package com.lokesh.weatherinfo.model;

import java.util.List;

/**
 * Created by Lokesh on 22-08-2015.
 */
public class WeatherList {

    private static WeatherList instance = new WeatherList();
    private WeatherList(){}

    public static WeatherList getInstance(){
        return instance;
    }


    private  Weather currentWeather;

    public  void addWeather(Weather weather) {
        currentWeather = weather;
    }

    public  Weather getCurrentWeather() {
        return currentWeather;
    }
}
