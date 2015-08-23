package com.lokesh.weatherinfo.UI;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lokesh.weatherinfo.Adapter.ForcastAdapter;
import com.lokesh.weatherinfo.Provider.WeatherProvider;
import com.lokesh.weatherinfo.R;
import com.lokesh.weatherinfo.model.Weather;
import com.lokesh.weatherinfo.model.WeatherForcast;
import com.lokesh.weatherinfo.model.WeatherList;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ForcastWeatherFragment extends ListFragment  {

    private ArrayList<WeatherForcast> list;
    private String location;
    private RequestQueue requestQueue;
    private SharedPreferences preference;
    private SimpleDateFormat sdf;
    private Calendar cal;
    private ListView listView;
    private SharedPreferences prefs;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sdf = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault());
        cal = Calendar.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        setHasOptionsMenu(true);
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forcast_weather,container,false);
        listView = (ListView)v.findViewById(android.R.id.list);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        location = preference.getString("cityName", null);
        getForcast();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    private void setAdapter(){
        ForcastAdapter adapter = new ForcastAdapter(getActivity(),R.layout.row_item_forcast,list);
        listView.setAdapter(adapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(list == null){
                progressDialog = ProgressDialog.show(getActivity(),
                        "Please wait...", "Loading Forcast Details", true, true);
            }
        }
    }

    private void getForcast() {
        if(location != null) {
            WeatherProvider.getWeatherForcast(location, requestQueue, new WeatherProvider.WeatherForcastListener() {
                @Override
                public void onWeatherResponse(ArrayList<WeatherForcast> forcast) {
                    if(progressDialog != null){
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    list = forcast;
                    setAdapter();
                }
            });
        }
    }


}
