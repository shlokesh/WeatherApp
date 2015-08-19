package com.lokesh.weatherinfo.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Window;

import com.lokesh.weatherinfo.R;


public class SettingActivity extends PreferenceActivity {

    public void onCreate(Bundle Bundle) {
        super.onCreate(Bundle);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String action = getIntent().getAction();

        addPreferencesFromResource(R.xml.settings);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Preference prefLocation = getPreferenceScreen().findPreference("location");
        Preference prefTemp = getPreferenceScreen().findPreference("temperature");

       prefLocation.setSummary(getResources().getText(R.string.location_summary) + " " + prefs.getString("cityName", null) + "," + prefs.getString("country", null));

        String unit =  prefs.getString("temperature", null) != null ? "°" + prefs.getString("temperature", null).toUpperCase() : "";
        prefTemp.setSummary(getResources().getText(R.string.temp_summary) + " " + unit);


    }
}
