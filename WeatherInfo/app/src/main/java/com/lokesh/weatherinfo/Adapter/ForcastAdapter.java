package com.lokesh.weatherinfo.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lokesh.weatherinfo.Provider.ImageProvider;
import com.lokesh.weatherinfo.Provider.ImageProviderImpl;
import com.lokesh.weatherinfo.R;
import com.lokesh.weatherinfo.model.WeatherForcast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class ForcastAdapter extends ArrayAdapter<WeatherForcast> {

    private ArrayList<WeatherForcast> list;
    private Context context;
    private TextView day;
    private TextView desc;
    private TextView temp;
    private ImageView img;
    private RequestQueue requestQueue;
    private Bitmap mPlaceHolderBitmap;
    private String tempUnit;
    private double temperature;
    private SharedPreferences preference;

    public ForcastAdapter(Context c,int resourceId, ArrayList<WeatherForcast> list){
        super(c,resourceId,list);
        this.context = c;
        this.list = list;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        preference = PreferenceManager.getDefaultSharedPreferences(context);
        tempUnit = preference.getString("temperature", null);
    }
    static class ViewHolderItem {

        TextView day;
        TextView desc;
        TextView temp;
        ImageView img;

    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;

        //View view = convertView;
        if (convertView  == null) {
            convertView  = LayoutInflater.from(context).inflate(R.layout.row_item_forcast, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.descrWeather);
            viewHolder.temp = (TextView) convertView.findViewById(R.id.temp);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);

        }else{
             viewHolder = (ViewHolderItem) convertView.getTag();
        }


        WeatherForcast forcast = list.get(position);
        viewHolder.day.setText(forcast.getDate());
        viewHolder.desc.setText(forcast.getDescr());
        temperature = forcast.getTemp() - 273.5;
        if("f".equalsIgnoreCase(tempUnit)){
            temperature = (1.8 * temperature) + 32.0;
        }
        viewHolder.temp.setText(String.valueOf((int)temperature) + "°");
        String icon = forcast.getIcon();
        ImageProviderImpl provider = new ImageProviderImpl();
        ImageTask task = new ImageTask(viewHolder.img);
        final AsyncDrawable asyncDrawable =
                new AsyncDrawable(context.getResources(), mPlaceHolderBitmap, task);
        viewHolder.img.setImageDrawable(asyncDrawable);
        provider.getImage(icon, requestQueue,task);
        return convertView;
    }

    private class ImageTask implements ImageProvider.WeatherImageListener{

        private final WeakReference<ImageView> imageViewReference;
        public ImageTask(ImageView imageView){
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        @Override
        public void onImageReady(Bitmap image) {

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                final ImageTask imageTask =
                        getImageTask(imageView);
                if (this == imageTask && imageView != null) {
                    imageView.setImageBitmap(image);
                }
            }
        }
    }

    private static ImageTask getImageTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getImageTask();
            }
        }
        return null;
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<ImageTask> imageTask;
        public AsyncDrawable(Resources res, Bitmap bitmap,
                             ImageTask task) {
            super(res, bitmap);
            imageTask =
                    new WeakReference<ImageTask>(task);
        }

        public ImageTask getImageTask() {
            return imageTask.get();
        }
    }
}
