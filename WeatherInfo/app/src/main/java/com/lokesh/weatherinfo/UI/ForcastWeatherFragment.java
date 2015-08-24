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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.app.ActionBarActivity;
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
    private ForcastAdapter adapter;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sdf = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault());
        cal = Calendar.getInstance();
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
        if(adapter == null) {
            adapter = new ForcastAdapter(getActivity(), R.layout.row_item_forcast, list);
            listView.setAdapter(adapter);
        }else{
            adapter.clear();
            if (list != null){
                for (WeatherForcast object : list) {
                    adapter.insert(object, adapter.getCount());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(location + ", Forcast");
            if(list == null){
               showDialog();
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

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // super.onCreateOptionsMenu(inflater,menu);
        inflater.inflate(R.menu.weather, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh :
                showDialog();
                getForcast();
            default:return true;
        }
    }

    private void showDialog(){
        progressDialog = ProgressDialog.show(getActivity(),
                "Please wait...", "Loading Forcast Details", true, true);
    }
}
