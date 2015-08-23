package com.lokesh.weatherinfo.Parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.lokesh.weatherinfo.model.PlaceInfo;
import com.lokesh.weatherinfo.model.Weather;
import com.lokesh.weatherinfo.model.WeatherForcast;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static java.util.Calendar.DAY_OF_WEEK;


public class JSONParser {
    private static  String TAG = "JSONParser";
	
	public static Weather getWeather(String jsonStr)  {
		Weather weather = new Weather();

		try {
            JSONObject jObj = new JSONObject(jsonStr);


            PlaceInfo pi = new PlaceInfo();

            JSONObject cord = getObject("coord", jObj);
            pi.setLatitude(getFloat("lat", cord));
            pi.setLongitude(getFloat("lon", cord));

            JSONObject sysObj = getObject("sys", jObj);
            pi.setCountry(getString("country", sysObj));
            pi.setSunrise(getInt("sunrise", sysObj));
            pi.setSunset(getInt("sunset", sysObj));
            pi.setCity(getString("name", jObj));
            weather.placeInfo = pi;


            JSONArray jArr = jObj.getJSONArray("weather");


            JSONObject curweather = jArr.getJSONObject(0);
            weather.currentWeather.setWeatherId(getInt("id", curweather));
            weather.currentWeather.setDescr(getString("description", curweather));
            weather.currentWeather.setCondition(getString("main", curweather));
            weather.currentWeather.setIcon(getString("icon", curweather));

            JSONObject main = getObject("main", jObj);
            weather.currentWeather.setHumidity(getInt("humidity", main));
            weather.currentWeather.setPressure(getInt("pressure", main));
            weather.temperature.setMaxTemp(getDouble("temp_max", main));
            weather.temperature.setMinTemp(getDouble("temp_min", main));
            weather.temperature.setTemp(getDouble("temp", main));


            JSONObject wind = getObject("wind", jObj);
            weather.wind.setSpeed(getFloat("speed", wind));
            weather.wind.setDeg(getFloat("deg", wind));


            JSONObject cloud = getObject("clouds", jObj);
            weather.clouds.setPerc(getInt("all", cloud));
        }catch(JSONException e){

        }
        return weather;
	}

    public static ArrayList<WeatherForcast> getWeatherForcast(String jsonStr)  {
        ArrayList<WeatherForcast> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String currentDate = sdf.format(cal.getTime());
        boolean inserted = true;
        Date date = null;
        String dt="";
        try {
            JSONObject jObj = new JSONObject(jsonStr);
            JSONObject cityObj = getObject("city",jObj);
            String city = getString("name", cityObj);
            JSONArray jArr = jObj.getJSONArray("list");
            for (int j = 0; j < jArr.length(); j++) {
                JSONObject curweather = jArr.getJSONObject(j);
                try {
                    dt = (getString("dt_txt", curweather));
                    date = sdf.parse(dt);
                    dt = sdf.format(date);
                    cal.setTime(sdf.parse(currentDate));
                    if(inserted) {
                        cal.add(Calendar.DATE, 1);  // advance by 1 day.
                    }
                    currentDate = sdf.format(cal.getTime());
                }catch(ParseException e){
                    e.printStackTrace();
                }
                if(dt.equals(currentDate)) {
                    WeatherForcast forcast = new WeatherForcast();
                    forcast.setDate(getWeekDay(date));
                    JSONObject main = getObject("main", curweather);
                    forcast.setTemp(getDouble("temp", main));
                    JSONArray weatherArr = curweather.getJSONArray("weather");
                    JSONObject weatherInnerObject = weatherArr.getJSONObject(0);
                    forcast.setDescr(getString("description", weatherInnerObject));
                    forcast.setIcon(getString("icon", weatherInnerObject));
                    list.add(forcast);
                    inserted=true;
                }else inserted = false;
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    private static String getWeekDay(Date date){
       return new SimpleDateFormat("EEEE").format(date);
    }
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

    private static double  getDouble(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getDouble(tagName);
    }

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}
	
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}

}
