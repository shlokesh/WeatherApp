package com.lokesh.weatherinfo.model;


public class WeatherForcast {

    private double temp;
    private String date;
    private String descr;
    private String icon;

    public void setTemp(double temp){
        this.temp = temp;
    }

    public double getTemp(){
        return temp;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setDescr(String descr){
        this.descr = descr;
    }

    public String getDescr(){
        return descr;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getIcon(){
        return icon;
    }
}
