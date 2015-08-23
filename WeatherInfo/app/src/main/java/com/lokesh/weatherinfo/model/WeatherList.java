package com.lokesh.weatherinfo.model;

import java.util.ArrayList;
import java.util.List;


public class WeatherList {

    private static WeatherList instance = new WeatherList();
    private WeatherList(){}

    public static WeatherList getInstance(){
        return instance;
    }


    private  Weather currentWeather;
    private ArrayList<Weather> forcast = new ArrayList<>();

    public  void addWeather(Weather weather) {
        currentWeather = weather;
    }

    public  Weather getCurrentWeather() {
        return currentWeather;
    }

    public void addForcast(Weather weather){
        forcast.add(weather);
    }

    public ArrayList<Weather> getForcast(){
        return forcast;
    }
}
