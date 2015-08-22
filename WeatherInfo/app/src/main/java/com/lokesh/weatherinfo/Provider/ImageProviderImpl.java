package com.lokesh.weatherinfo.Provider;


import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;

public class ImageProviderImpl implements ImageProvider {

    private static String IMG_URL = "http://openweathermap.org/img/w/";

    @Override
    public Bitmap getImage(String icon, RequestQueue requestQueue, final ImageProvider.WeatherImageListener listener) {
        String imageURL = IMG_URL + icon + ".png"; ;
        ImageRequest ir = new ImageRequest(imageURL, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap response) {
                if (listener != null)
                    listener.onImageReady(response);
            }
        }, 0, 0, null, null);

        requestQueue.add(ir);
        return null;
    }
}
