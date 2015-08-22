package com.lokesh.weatherinfo.UI;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.DialogFragment;
import android.app.ProgressDialog;
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

    public static final int DIALOG_LOADING = 1;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
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
        setCurrentWeather();

    }

    private void setCurrentWeather(){
        if(weatherRepository.getCurrentWeather()!= null){
            Weather weather = weatherRepository.getCurrentWeather();
            int code = weather.currentWeather.getWeatherId();
            String icon = weather.currentWeather.getIcon();
            double temperature =weather.temperature.getTemp() - 273.5;
            double minTemp = weather.temperature.getMinTemp() - 273.5;
            double maxTemp = weather.temperature.getMaxTemp() - 273.5;
            String color = Utils.getColorCodeForTemperature(temperature);
            if(!celsius){
                temperature = (1.8 * temperature) + 32.0;
                minTemp = (1.8 * minTemp) + 32.0;
                maxTemp = (1.8 * maxTemp) + 32.0;

            }
            int temps = (int) temperature;
            temp.setText(String.valueOf(temps));
            humidity.setText(String.valueOf(weather.currentWeather.getHumidity()) + " %");
            wind.setText(String.valueOf(weather.wind.getSpeed()) + " km/h");
            maxtemp.setText(String.valueOf((int)maxTemp) + (celsius ? "°C" : "°F"));
            mintemp.setText(String.valueOf((int) minTemp)+ (celsius ? "°C" : "°F"));
            rain.setText(String.valueOf(weather.rain.getAmmount()) + " mm");
            if(celsius) unit.setText("°C");
            else unit.setText("°F");
            desc.setText(weather.currentWeather.getDescr());
            time.setText("Last Updated :" + sdf.format(cal.getTime()));

           // int color = getBackgroundColor(weather.temperature.getTemp());
            tempLayout.setBackgroundColor(Color.parseColor(color));

            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(location + ", Current");
            ((ActionBarActivity) getActivity()).getSupportActionBar()
                    .setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
            ImageProviderImpl provider = new ImageProviderImpl();
            provider.getImage(icon, requestQueue, new ImageProvider.WeatherImageListener() {
                @Override
                public void onImageReady(Bitmap image) {
                    weatherImage.setImageBitmap(image);
                }
            });
        }
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

    public int getBackgroundColor(double temp) {
        temp = 50;
        Log.d("lokesh","temp =" + temp);
        String mColor;
        mColor = "#c3eee7";
        float tmpKelvin;
        double tmpCalc;
        int r,g,b;

        tmpKelvin =(float) (temp * 255.928);

        if (tmpKelvin < 1000)
            tmpKelvin = 1000;
        if (tmpKelvin > 40000)
            tmpKelvin = 40000;

        if (tmpKelvin <= 66) {
            r = 255;
        }
        else{
            tmpCalc = (double) tmpKelvin - 60;
            tmpCalc = 329.698727446 *(Math.pow(tmpCalc,0.1332047592)); // (tmpCalc ^ -0.1332047592)
            r = (int)tmpCalc;
            if(r<0) r=0;
            if(r>255)r=255;
        }

        if(tmpKelvin <= 66){
            tmpCalc = tmpKelvin;
            tmpCalc = 99.4708025861 * Math.log(tmpCalc) - 161.1195681661;
             g = (int)tmpCalc;
        }else{
            tmpCalc = tmpKelvin - 60;
            tmpCalc = 288.1221695283 *Math.pow (tmpCalc , -0.0755148492);
            g = (int)tmpCalc;
            if(g<0) g=0;
            if(g>255)g=255;
        }


        if(tmpKelvin >= 66)
            b = 255;
        else if(tmpKelvin <= 19)
            b = 0;
        else{
            tmpCalc = tmpKelvin - 10;
            tmpCalc = 138.5177312231 * Math.log(tmpCalc) - 305.0447927307;
            b = (int)tmpCalc;
            if(b<0) b = 0;
            if(b>255) b = 255;
        }
        Log.d("rinku","r = " +r + "g = " +g + "b = " +b);
        mColor = "#"+r+r+g+g+b+b;
        Log.d("lokesh","color =" + mColor);
        return Color.rgb(r,g,b);
    }

}
