package com.lokesh.weatherinfo.Settings;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.lokesh.weatherinfo.Provider.WeatherProvider;
import com.lokesh.weatherinfo.R;
import com.lokesh.weatherinfo.model.Weather;
import com.lokesh.weatherinfo.model.WeatherList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class LocationActivity  extends ListActivity implements View.OnClickListener {

    private Button searchButton;
    private EditText editText;
    private RequestQueue requestQueue;
    private String city;
    private Weather weatherInfo;
    private ProgressDialog progressDialog;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.location_activity);

        searchButton = (Button) findViewById(R.id.search);
        editText = (EditText) findViewById(R.id.edit);
        searchButton.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
    }



    @Override
    public void onClick(View v){
        progressDialog = ProgressDialog.show(this,
                "Please wait...", "Searching", true, true);

        String location = editText.getText().toString();

        WeatherProvider.getWeather(location, requestQueue, new WeatherProvider.WeatherClientListener() {
            @Override
            public void onWeatherResponse(Weather weather) {
                if (progressDialog!=null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                weatherInfo = weather;
                city = weather.placeInfo.getCity();
                displayResult(city);
            }
        });

    }

    private void displayResult(String city){
        String[] result = {city};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, result);
        setListAdapter(adapter);
    }

             @Override
             protected void onListItemClick(ListView l, View v, int position, long id) {
                 super.onListItemClick(l, v, position, id);
                 int itemPosition = position;
                 String itemValue = (String) l.getItemAtPosition(position);
                 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                 SharedPreferences.Editor editor = prefs.edit();
                 editor.putString("cityName", itemValue);
                 editor.commit();
                 WeatherList.getInstance().addWeather(weatherInfo);
                 finish();
             }
    }
