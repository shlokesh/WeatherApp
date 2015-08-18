package com.lokesh.weatherinfo.UI;

import com.lokesh.weatherinfo.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CurrentWeatherFragment extends Fragment {

    Activity owner;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_weather,container,false);
        return v;
    }


}
