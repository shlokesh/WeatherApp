package com.lokesh.weatherinfo.Parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.lokesh.weatherinfo.model.PlaceInfo;
import com.lokesh.weatherinfo.model.Weather;


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
		
		Log.d(TAG, "Weather =" + weather);
		return weather;
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
