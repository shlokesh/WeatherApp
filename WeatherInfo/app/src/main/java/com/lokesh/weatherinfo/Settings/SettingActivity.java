package com.lokesh.weatherinfo.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.view.Window;

import com.lokesh.weatherinfo.R;


public class SettingActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    private Preference prefLocation;
    private Preference prefTemp;
    private SharedPreferences prefs;


    public void onCreate(Bundle Bundle) {
        super.onCreate(Bundle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String action = getIntent().getAction();

        addPreferencesFromResource(R.xml.settings);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefLocation = getPreferenceScreen().findPreference("location");
        prefTemp = getPreferenceScreen().findPreference("temperature");




    }

    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        String city =  prefs.getString("cityName", null) != null ?  prefs.getString("cityName", null).toUpperCase() : " Not Set";

        prefLocation.setSummary(getResources().getText(R.string.location_summary) + "-" + city);

        String unit =  prefs.getString("temperature", null) != null ? "°" + prefs.getString("temperature", null).toUpperCase() : "";
        prefTemp.setSummary(getResources().getText(R.string.temp_summary) + " " + unit);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("temperature")){
            String unit =  prefs.getString("temperature", null) != null ? "°" + prefs.getString("temperature", null).toUpperCase() : "";
            prefTemp.setSummary(getResources().getText(R.string.temp_summary) + " " + unit);

        }
    }
}
