package com.lokesh.weatherinfo.UI;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.preference.Preference;
import android.support.v7.app.ActionBarActivity;
import com.lokesh.weatherinfo.Provider.ImageProvider;
import com.lokesh.weatherinfo.Provider.ImageProviderImpl;
import com.lokesh.weatherinfo.Provider.WeatherProvider;
import com.lokesh.weatherinfo.R;
import com.lokesh.weatherinfo.Utils;
import com.lokesh.weatherinfo.model.Weather;
import com.lokesh.weatherinfo.model.WeatherList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CurrentWeatherFragment extends Fragment {


    private RequestQueue requestQueue;
    private SharedPreferences preference;

    private TextView temp;
    private TextView desc;
    private TextView maxtemp;
    private TextView mintemp;
    private TextView wind;
    private TextView rain;
    private TextView humidity;
    private TextView unit;
    private TextView time;
    private ImageView weatherImage;
    private RelativeLayout tempLayout;

    private Preference prefLocation;
    private Preference prefTemp;
    private String location;
    private String tempUnit;
    private boolean celsius = true;
    private ProgressDialog progressDialog;
    private WeatherList weatherRepository;
    private SimpleDateFormat sdf;
    private Calendar cal;
    private boolean notify;
    private NotificationManager notificationManager;
    private double temperature;
    private Bitmap weatherBitmap;
    public static final int DIALOG_LOADING = 1;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        weatherRepository = WeatherList.getInstance();
        sdf = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault());
        cal = Calendar.getInstance();
        setHasOptionsMenu(true);
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_weather,container,false);

        temp = (TextView) v.findViewById(R.id.temp);
        desc =  (TextView) v.findViewById(R.id.descrWeather);
        maxtemp =  (TextView) v.findViewById(R.id.maxTemp);
        mintemp =  (TextView) v.findViewById(R.id.minTemp);
        rain =  (TextView) v.findViewById(R.id.rain);
        wind =  (TextView) v.findViewById(R.id.wind);
        humidity =  (TextView) v.findViewById(R.id.humidity);
        unit =  (TextView) v.findViewById(R.id.tempUnit);
        time =  (TextView) v.findViewById(R.id.time);
        weatherImage = (ImageView) v.findViewById(R.id.imgWeather);
        tempLayout = (RelativeLayout) v.findViewById(R.id.tempLayout);
        return v;
    }

    public void onResume(){
        super.onResume();

        location = preference.getString("cityName", null);
        tempUnit = preference.getString("temperature", null);
        if("f".equalsIgnoreCase(tempUnit))
            celsius = false;
        notify = preference.getBoolean("notification", true);
        if(!notify){
            notificationManager.cancel(1);
        }else{
            if(weatherRepository.getCurrentWeather() != null)
                notifyWeather(weatherRepository.getCurrentWeather(),weatherBitmap);
        }
        setCurrentWeather();

    }

    private void setCurrentWeather(){
        if(weatherRepository.getCurrentWeather()!= null){
            final Weather weather = weatherRepository.getCurrentWeather();
            int code = weather.currentWeather.getWeatherId();
            String icon = weather.currentWeather.getIcon();
            temperature =weather.temperature.getTemp() - 273.5;
            double minTemp = weather.temperature.getMinTemp() - 273.5;
            double maxTemp = weather.temperature.getMaxTemp() - 273.5;
            String color = Utils.getColorCodeForTemperature(temperature);
            if(!celsius){
                temperature = (1.8 * temperature) + 32.0;
                minTemp = (1.8 * minTemp) + 32.0;
                maxTemp = (1.8 * maxTemp) + 32.0;

            }
            temp.setText(String.valueOf((int)temperature));
            humidity.setText(String.valueOf(weather.currentWeather.getHumidity()) + " %");
            wind.setText(String.valueOf(weather.wind.getSpeed()) + " km/h");
            maxtemp.setText(String.valueOf((int)maxTemp) + (celsius ? "°C" : "°F"));
            mintemp.setText(String.valueOf((int) minTemp)+ (celsius ? "°C" : "°F"));
            rain.setText(String.valueOf(weather.rain.getAmmount()) + " mm");
            if(celsius) unit.setText("°C");
            else unit.setText("°F");
            desc.setText(weather.currentWeather.getDescr());
            time.setText("Last Updated :" + sdf.format(cal.getTime()));
            tempLayout.setBackgroundColor(Color.parseColor(color));

            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(location + ", Current");
            ((ActionBarActivity) getActivity()).getSupportActionBar()
                    .setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
            ImageProviderImpl provider = new ImageProviderImpl();
            provider.getImage(icon, requestQueue, new ImageProvider.WeatherImageListener() {
                @Override
                public void onImageReady(Bitmap image) {
                    weatherImage.setImageBitmap(image);
                    if(notify) notifyWeather(weather, image);
                    weatherBitmap = image;
                }
            });

        }
    }

    private void notifyWeather(Weather weather,Bitmap img){

        int icon = R.drawable.weather;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, "Weather Info", when);

        RemoteViews contentView = new RemoteViews(getActivity().getPackageName(), R.layout.notification);
        contentView.setImageViewBitmap(R.id.notification_image, img);
        contentView.setTextViewText(R.id.notification_condition, weather.currentWeather.getDescr());
        contentView.setTextViewText(R.id.notification_updated, location);
        contentView.setTextViewText(R.id.notification_degrees, String.valueOf((int)temperature) + "°");
        notification.contentView = contentView;
        notificationManager.notify(1, notification);
    }

    private void refreshWeather() {
        if(location != null) {
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please wait...", "Refreshing Weather Details.", true, true);
            WeatherProvider.getWeather(location, requestQueue, new WeatherProvider.WeatherClientListener() {
                @Override
                public void onWeatherResponse(Weather weather) {

                    if (progressDialog!=null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    weatherRepository.addWeather(weather);
                    setCurrentWeather();
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
                refreshWeather();
            default:return true;
        }
    }

}
