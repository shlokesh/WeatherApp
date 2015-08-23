package com.lokesh.weatherinfo.Provider;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

public interface ImageProvider {

   public Bitmap getImage(String code,RequestQueue requestQueue, WeatherImageListener listener);


    public static interface WeatherImageListener {
        public void onImageReady(Bitmap image);
    }
}
