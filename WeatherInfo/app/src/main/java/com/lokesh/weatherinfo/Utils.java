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
            case 0: color = "#FF8080"; break;
            case 1: color = "#FFE6E6"; break;
            case 2: color = "#FF5CAD"; break;
            case 3: color = "#FFAD5C"; break;
            case 4: color = "#FFFFCC"; break;
            case 5: color = "#CCFF99"; break;
            case 6: color = "#CCCCFF"; break;
            case 7: color = "#CCFFFF"; break;
            case 8: color = "#BFD3FF"; break;
            case 9: color = "#CCEBFF"; break;
            case 10: color = "#66C2FF"; break;
            default: color = "#FF8080";
        }
        return color;
    }
}
