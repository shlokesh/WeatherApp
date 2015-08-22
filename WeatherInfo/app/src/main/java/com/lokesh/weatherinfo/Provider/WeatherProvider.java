package com.lokesh.weatherinfo.Provider;


import android.util.Log;

import com.lokesh.weatherinfo.Parser.JSONParser;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lokesh.weatherinfo.model.Weather;

import org.json.JSONException;

import java.net.URL;

public class WeatherProvider {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    private static String TAG = "WeatherProvider";

    public static interface WeatherClientListener {
        public void onWeatherResponse(Weather weather);
    }

    public static void getWeather(String location,  RequestQueue rq, final WeatherClientListener listener) {

        String url = BASE_URL + location;
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonStr)  {
                Log.d(TAG, "JSON response = " + jsonStr);
                Weather result = JSONParser.getWeather(jsonStr);
                listener.onWeatherResponse(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        rq.add(req);
    }
}
