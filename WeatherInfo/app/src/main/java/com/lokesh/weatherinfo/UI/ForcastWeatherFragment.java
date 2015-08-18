package com.lokesh.weatherinfo.UI;

import com.lokesh.weatherinfo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ForcastWeatherFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forcast_weather,container,false);
        return v;
    }
}
