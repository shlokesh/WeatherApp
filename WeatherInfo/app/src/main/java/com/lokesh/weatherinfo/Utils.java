package com.lokesh.weatherinfo;

import android.util.Log;

/**
 * Created by Lokesh on 22-08-2015.
 */
public class Utils {

    //get Color as per temp value.
    // less temp have more red value, while high temp has more blue value.

    public static String getColorCodeForTemperature(double temp){
        int value = (int)(temp/5);
        Log.d("rinku", "value =" + value);
        String color;
        switch(value){
            case 0: color = "#FF8080";
            case 1: color = "#FFE6E6";
            case 2: color = "#FF5CAD";
            case 3: color = "#FFAD5C";
            case 4: color = "#FFFFCC";
            case 5: color = "#CCFF99";
            case 6: color = "#CCCCFF";
            case 7: color = "#CCFFFF";
            case 8: color = "#BFD3FF";
            case 9: color = "#CCEBFF";
            case 10: color = "#66C2FF";
            default: color = "#FF8080";
        }
        return color;
    }
}
